package io.github.zhc.dev.friend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.enums.ContestListType;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.friend.mapper.contest.ContestMapper;
import io.github.zhc.dev.friend.mapper.contest.ContestQuestionMapper;
import io.github.zhc.dev.friend.mapper.user.UserContestMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.dto.contest.ContestRankRequest;
import io.github.zhc.dev.friend.model.entity.contest.Contest;
import io.github.zhc.dev.friend.model.entity.contest.ContestQuestion;
import io.github.zhc.dev.friend.model.entity.user.UserContest;
import io.github.zhc.dev.friend.model.vo.contest.ContestRankVO;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.security.exception.ServiceException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhc.dev
 * @date 2025/4/9 20:41
 */
@Component
public class ContestCacheManager {

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestQuestionMapper contestQuestionMapper;

    @Resource
    private UserContestMapper userContestMapper;

    @Resource
    private RedisService redisService;

    public Long getListSize(Integer examListType, Long userId) {
        String examListKey = getExamListKey(examListType, userId);
        return redisService.getListSize(examListKey);
    }

    public Long getExamQuestionListSize(Long examId) {
        String examQuestionListKey = getContestQuestionList(examId);
        return redisService.getListSize(examQuestionListKey);
    }

    public Long getRankListSize(Long examId) {
        return redisService.getListSize(getExamRankListKey(examId));
    }

    public List<ContestVO> getContestVOList(ContestQueryRequest contestQueryRequest, Long userId) {
        int start = (contestQueryRequest.getPageNum() - 1) * contestQueryRequest.getPageSize();
        int end = start + contestQueryRequest.getPageSize() - 1; //下标需要 -1
        String examListKey = getExamListKey(contestQueryRequest.getType(), userId);
        List<Long> examIdList = redisService.getCacheListByRange(examListKey, start, end, Long.class);
        List<ContestVO> examVOList = assembleContestVOList(examIdList);
        if (CollectionUtil.isEmpty(examVOList)) {
            //说明redis中数据可能有问题 从数据库中查数据并且重新刷新缓存
            examVOList = getExamListByDB(contestQueryRequest, userId); //从数据库中获取数据
            refreshCache(contestQueryRequest.getType(), userId);
        }
        return examVOList;
    }

    public List<ContestRankVO> getExamRankList(ContestRankRequest contestRankRequest) {
        int start = (contestRankRequest.getPageNum() - 1) * contestRankRequest.getPageSize();
        int end = start + contestRankRequest.getPageSize() - 1; //下标需要 -1
        return redisService.getCacheListByRange(getExamRankListKey(contestRankRequest.getContestId()), start, end, ContestRankVO.class);
    }

    public List<Long> getAllUserContestList(Long userId) {
        String examListKey = CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
        List<Long> userExamIdList = redisService.getCacheListByRange(examListKey, 0, -1, Long.class);
        if (CollectionUtil.isNotEmpty(userExamIdList)) {
            return userExamIdList;
        }
        List<UserContest> userExamList =
                userContestMapper.selectList(new LambdaQueryWrapper<UserContest>().eq(UserContest::getUserId, userId));
        if (CollectionUtil.isEmpty(userExamList)) {
            return null;
        }
        refreshCache(ContestListType.USER_CONTEST_LIST.getValue(), userId);
        return userExamList.stream().map(UserContest::getContestId).collect(Collectors.toList());
    }

    public void addUserContestCache(Long userId, Long examId) {
        String userExamListKey = getUserContestListKey(userId);
        redisService.leftPushForList(userExamListKey, examId);
    }

    public Long getFirstQuestion(Long examId) {
        return redisService.indexForList(getContestQuestionList(examId), 0, Long.class);
    }

    public Long preQuestion(Long examId, Long questionId) {
        Long index = redisService.indexOfForList(getContestQuestionList(examId), questionId);
        if (index == 0) {
            throw new ServiceException(ResultCode.FAILED_FIRST_QUESTION);
        }
        return redisService.indexForList(getContestQuestionList(examId), index - 1, Long.class);
    }

    public Long nextQuestion(Long examId, Long questionId) {
        Long index = redisService.indexOfForList(getContestQuestionList(examId), questionId);
        long lastIndex = getExamQuestionListSize(examId) - 1;
        if (index == lastIndex) {
            throw new ServiceException(ResultCode.FAILED_LAST_QUESTION);
        }
        return redisService.indexForList(getContestQuestionList(examId), index + 1, Long.class);
    }

    //刷新缓存逻辑
    public void refreshCache(Integer examListType, Long userId) {
        List<Contest> contestList = new ArrayList<>();
        if (ContestListType.CONTEST_UNFINISHED_LIST.getValue().equals(examListType)) {
            //查询未完赛的竞赛列表
            contestList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                    .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                    .gt(Contest::getEndTime, LocalDateTime.now())
                    .eq(Contest::getStatus, Constants.TRUE)
                    .orderByDesc(Contest::getCreateTime));
        } else if (ContestListType.CONTEST_HISTORY_LIST.getValue().equals(examListType)) {
            //查询历史竞赛
            contestList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                    .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                    .le(Contest::getEndTime, LocalDateTime.now())
                    .eq(Contest::getStatus, Constants.TRUE)
                    .orderByDesc(Contest::getCreateTime));
        } else if (ContestListType.USER_CONTEST_LIST.getValue().equals(examListType)) {
            List<ContestVO> examVOList = userContestMapper.selectUserContestList(userId);
            contestList = BeanUtil.copyToList(examVOList, Contest.class);
        }
        if (CollectionUtil.isEmpty(contestList)) {
            return;
        }

        Map<String, Contest> examMap = new HashMap<>();
        List<Long> examIdList = new ArrayList<>();
        for (Contest exam : contestList) {
            examMap.put(getDetailKey(exam.getContestId()), exam);
            examIdList.add(exam.getContestId());
        }
        redisService.multiSet(examMap);  //刷新详情缓存
        redisService.deleteObject(getExamListKey(examListType, userId));
        redisService.rightPushAll(getExamListKey(examListType, userId), examIdList);      //刷新列表缓存
    }

    public void refreshExamQuestionCache(Long examId) {
        List<ContestQuestion> examQuestionList = contestQuestionMapper.selectList(new LambdaQueryWrapper<ContestQuestion>()
                .select(ContestQuestion::getQuestionId)
                .eq(ContestQuestion::getContestId, examId)
                .orderByAsc(ContestQuestion::getDisplayOrder));
        if (CollectionUtil.isEmpty(examQuestionList)) {
            return;
        }
        List<Long> examQuestionIdList = examQuestionList.stream().map(ContestQuestion::getQuestionId).toList();
        redisService.rightPushAll(getContestQuestionList(examId), examQuestionIdList);
        //节省 redis缓存资源
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(),
                LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
        redisService.expire(getContestQuestionList(examId), seconds, TimeUnit.SECONDS);
    }

    public void refreshExamRankCache(Long examId) {
        List<ContestRankVO> examRankVOList = userContestMapper.selectContestRankList(examId);
        if (CollectionUtil.isEmpty(examRankVOList)) {
            return;
        }
        redisService.rightPushAll(getExamRankListKey(examId), examRankVOList);
    }

    private List<ContestVO> getExamListByDB(ContestQueryRequest contestQueryRequest, Long userId) {
        PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
        if (ContestListType.USER_CONTEST_LIST.getValue().equals(contestQueryRequest.getType())) {
            //查询我的竞赛列表
            return userContestMapper.selectUserContestList(userId);
        } else {
            //查询C端的竞赛列表
            return contestMapper.selectContestList(contestQueryRequest);
        }
    }

    private List<ContestVO> assembleContestVOList(List<Long> examIdList) {
        if (CollectionUtil.isEmpty(examIdList)) {
            //说明redis当中没数据 从数据库中查数据并且重新刷新缓存
            return null;
        }
        //拼接redis当中key的方法 并且将拼接好的key存储到一个list中
        List<String> detailKeyList = new ArrayList<>();
        for (Long examId : examIdList) {
            detailKeyList.add(getDetailKey(examId));
        }
        List<ContestVO> examVOList = redisService.multiGet(detailKeyList, ContestVO.class);
        CollUtil.removeNull(examVOList);
        if (CollectionUtil.isEmpty(examVOList) || examVOList.size() != examIdList.size()) {
            //说明redis中数据有问题 从数据库中查数据并且重新刷新缓存
            return null;
        }
        return examVOList;
    }

    private String getExamListKey(Integer examListType, Long userId) {
        if (ContestListType.CONTEST_UNFINISHED_LIST.getValue().equals(examListType)) {
            return CacheConstants.CONTEST_UNFINISHED_LIST_KEY;
        } else if (ContestListType.CONTEST_HISTORY_LIST.getValue().equals(examListType)) {
            return CacheConstants.CONTEST_HISTORY_LIST_KEY;
        } else {
            return CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
        }
    }

    private String getDetailKey(Long examId) {
        return CacheConstants.USER_DETAIL_KEY_PREFIX + examId;
    }

    private String getUserContestListKey(Long userId) {
        return CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
    }

    private String getContestQuestionList(Long examId) {
        return CacheConstants.CONTEST_QUESTION_LIST_KEY_PREFIX + examId;
    }

    private String getExamRankListKey(Long examId) {
        return CacheConstants.CONTEST_RANK_LIST_KEY_PREFIX + examId;
    }
}
