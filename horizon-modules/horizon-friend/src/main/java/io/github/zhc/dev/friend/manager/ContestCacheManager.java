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

    public Long getListSize(Integer contestListType, Long userId) {
        String contestListKey = getContestListKey(contestListType, userId);
        return redisService.getListSize(contestListKey);
    }

    public Long getContestQuestionListSize(Long contestId) {
        String contestQuestionListKey = getContestQuestionList(contestId);
        return redisService.getListSize(contestQuestionListKey);
    }

    public Long getRankListSize(Long contestId) {
        return redisService.getListSize(getContestRankListKey(contestId));
    }

    public List<ContestVO> getContestVOList(ContestQueryRequest contestQueryRequest, Long userId) {
        int start = (contestQueryRequest.getPageNum() - 1) * contestQueryRequest.getPageSize();
        int end = start + contestQueryRequest.getPageSize() - 1; //下标需要 -1
        String contestListKey = getContestListKey(contestQueryRequest.getType(), userId);
        List<Long> contestIdList = redisService.getCacheListByRange(contestListKey, start, end, Long.class);
        List<ContestVO> contestVOList = assembleContestVOList(contestIdList);
        if (CollectionUtil.isEmpty(contestVOList)) {
            //说明redis中数据可能有问题 从数据库中查数据并且重新刷新缓存
            contestVOList = getContestListByDB(contestQueryRequest, userId); //从数据库中获取数据
            refreshCache(contestQueryRequest.getType(), userId);
        }
        return contestVOList;
    }

    public List<ContestRankVO> getContestRankList(ContestRankRequest contestRankRequest) {
        int start = (contestRankRequest.getPageNum() - 1) * contestRankRequest.getPageSize();
        int end = start + contestRankRequest.getPageSize() - 1; //下标需要 -1
        return redisService.getCacheListByRange(getContestRankListKey(contestRankRequest.getContestId()), start, end, ContestRankVO.class);
    }

    public List<Long> getAllUserContestList(Long userId) {
        String contestListKey = CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
        List<Long> userContestIdList = redisService.getCacheListByRange(contestListKey, 0, -1, Long.class);
        if (CollectionUtil.isNotEmpty(userContestIdList)) {
            return userContestIdList;
        }
        List<UserContest> userContestList =
                userContestMapper.selectList(new LambdaQueryWrapper<UserContest>().eq(UserContest::getUserId, userId));
        if (CollectionUtil.isEmpty(userContestList)) {
            return null;
        }
        refreshCache(ContestListType.USER_CONTEST_LIST.getValue(), userId);
        return userContestList.stream().map(UserContest::getContestId).collect(Collectors.toList());
    }

    public void addUserContestCache(Long userId, Long contestId) {
        String userContestListKey = getUserContestListKey(userId);
        redisService.leftPushForList(userContestListKey, contestId);
    }

    public Long getFirstQuestion(Long contestId) {
        return redisService.indexForList(getContestQuestionList(contestId), 0, Long.class);
    }

    public Long preQuestion(Long contestId, Long questionId) {
        Long index = redisService.indexOfForList(getContestQuestionList(contestId), questionId);
        if (index == 0) {
            throw new ServiceException(ResultCode.FAILED_FIRST_QUESTION);
        }
        return redisService.indexForList(getContestQuestionList(contestId), index - 1, Long.class);
    }

    public Long nextQuestion(Long contestId, Long questionId) {
        Long index = redisService.indexOfForList(getContestQuestionList(contestId), questionId);
        long lastIndex = getContestQuestionListSize(contestId) - 1;
        if (index == lastIndex) {
            throw new ServiceException(ResultCode.FAILED_LAST_QUESTION);
        }
        return redisService.indexForList(getContestQuestionList(contestId), index + 1, Long.class);
    }

    //刷新缓存逻辑
    public void refreshCache(Integer contestListType, Long userId) {
        List<Contest> contestList = new ArrayList<>();
        if (ContestListType.CONTEST_UNFINISHED_LIST.getValue().equals(contestListType)) {
            //查询未完赛的竞赛列表
            contestList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                    .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                    .gt(Contest::getEndTime, LocalDateTime.now())
                    .eq(Contest::getStatus, Constants.TRUE)
                    .orderByDesc(Contest::getCreateTime));
        } else if (ContestListType.CONTEST_HISTORY_LIST.getValue().equals(contestListType)) {
            //查询历史竞赛
            contestList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                    .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                    .le(Contest::getEndTime, LocalDateTime.now())
                    .eq(Contest::getStatus, Constants.TRUE)
                    .orderByDesc(Contest::getCreateTime));
        } else if (ContestListType.USER_CONTEST_LIST.getValue().equals(contestListType)) {
            List<ContestVO> contestVOList = userContestMapper.selectUserContestList(userId);
            contestList = BeanUtil.copyToList(contestVOList, Contest.class);
        }
        if (CollectionUtil.isEmpty(contestList)) {
            return;
        }

        Map<String, Contest> contestMap = new HashMap<>();
        List<Long> contestIdList = new ArrayList<>();
        for (Contest contest : contestList) {
            contestMap.put(getDetailKey(contest.getContestId()), contest);
            contestIdList.add(contest.getContestId());
        }
        redisService.multiSet(contestMap);  //刷新详情缓存
        redisService.deleteObject(getContestListKey(contestListType, userId));
        redisService.rightPushAll(getContestListKey(contestListType, userId), contestIdList);      //刷新列表缓存
    }

    public void refreshContestQuestionCache(Long contestId) {
        List<ContestQuestion> contestQuestionList = contestQuestionMapper.selectList(new LambdaQueryWrapper<ContestQuestion>()
                .select(ContestQuestion::getQuestionId)
                .eq(ContestQuestion::getContestId, contestId)
                .orderByAsc(ContestQuestion::getDisplayOrder));
        if (CollectionUtil.isEmpty(contestQuestionList)) {
            return;
        }
        List<Long> contestQuestionIdList = contestQuestionList.stream().map(ContestQuestion::getQuestionId).toList();
        redisService.rightPushAll(getContestQuestionList(contestId), contestQuestionIdList);
        //节省 redis缓存资源
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(),
                LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
        redisService.expire(getContestQuestionList(contestId), seconds, TimeUnit.SECONDS);
    }

    public void refreshContestRankCache(Long contestId) {
        List<ContestRankVO> contestRankVOList = userContestMapper.selectContestRankList(contestId);
        if (CollectionUtil.isEmpty(contestRankVOList)) {
            return;
        }
        redisService.rightPushAll(getContestRankListKey(contestId), contestRankVOList);
    }

    private List<ContestVO> getContestListByDB(ContestQueryRequest contestQueryRequest, Long userId) {
        PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
        if (ContestListType.USER_CONTEST_LIST.getValue().equals(contestQueryRequest.getType())) {
            //查询我的竞赛列表
            return userContestMapper.selectUserContestList(userId);
        } else {
            //查询C端的竞赛列表
            return contestMapper.selectContestList(contestQueryRequest);
        }
    }

    private List<ContestVO> assembleContestVOList(List<Long> contestIdList) {
        if (CollectionUtil.isEmpty(contestIdList)) {
            //说明redis当中没数据 从数据库中查数据并且重新刷新缓存
            return null;
        }
        //拼接redis当中key的方法 并且将拼接好的key存储到一个list中
        List<String> detailKeyList = new ArrayList<>();
        for (Long contestId : contestIdList) {
            detailKeyList.add(getDetailKey(contestId));
        }
        List<ContestVO> contestVOList = redisService.multiGet(detailKeyList, ContestVO.class);
        CollUtil.removeNull(contestVOList);
        if (CollectionUtil.isEmpty(contestVOList) || contestVOList.size() != contestIdList.size()) {
            //说明redis中数据有问题 从数据库中查数据并且重新刷新缓存
            return null;
        }
        return contestVOList;
    }

    private String getContestListKey(Integer contestListType, Long userId) {
        if (ContestListType.CONTEST_UNFINISHED_LIST.getValue().equals(contestListType)) {
            return CacheConstants.CONTEST_UNFINISHED_LIST_KEY;
        } else if (ContestListType.CONTEST_HISTORY_LIST.getValue().equals(contestListType)) {
            return CacheConstants.CONTEST_HISTORY_LIST_KEY;
        } else {
            return CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
        }
    }

    private String getDetailKey(Long contestId) {
        return CacheConstants.USER_DETAIL_KEY_PREFIX + contestId;
    }

    private String getUserContestListKey(Long userId) {
        return CacheConstants.USER_CONTEST_LIST_KEY_PREFIX + userId;
    }

    private String getContestQuestionList(Long contestId) {
        return CacheConstants.CONTEST_QUESTION_LIST_KEY_PREFIX + contestId;
    }

    private String getContestRankListKey(Long contestId) {
        return CacheConstants.CONTEST_RANK_LIST_KEY_PREFIX + contestId;
    }
}
