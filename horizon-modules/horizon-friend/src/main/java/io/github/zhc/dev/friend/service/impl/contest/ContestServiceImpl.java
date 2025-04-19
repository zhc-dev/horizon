package io.github.zhc.dev.friend.service.impl.contest;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.common.core.utils.ThreadLocalUtil;
import io.github.zhc.dev.friend.manager.ContestCacheManager;
import io.github.zhc.dev.friend.manager.UserCacheManager;
import io.github.zhc.dev.friend.mapper.contest.ContestMapper;
import io.github.zhc.dev.friend.mapper.user.UserContestMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.dto.contest.ContestRankRequest;
import io.github.zhc.dev.friend.model.vo.contest.ContestRankVO;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;
import io.github.zhc.dev.friend.model.vo.user.UserVO;
import io.github.zhc.dev.friend.service.contest.ContestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:40
 */
@Service
public class ContestServiceImpl implements ContestService {
    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestCacheManager contestCacheManager;

    @Resource
    private UserCacheManager userCacheManager;

    @Resource
    private UserContestMapper userContestMapper;

    @Override
    public List<ContestVO> list(ContestQueryRequest contestQueryRequest) {
        PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
        return contestMapper.selectContestList(contestQueryRequest);
    }

    @Override
    public TableData listByCache(ContestQueryRequest contestQueryRequest) {
        //从redis当中获取  竞赛列表的数据
        Long total = contestCacheManager.getListSize(contestQueryRequest.getType(), null);
        List<ContestVO> contestVOList;
        if (total == null || total <= 0) {
            contestVOList = list(contestQueryRequest);
            contestCacheManager.refreshCache(contestQueryRequest.getType(), null);
            total = new PageInfo<>(contestVOList).getTotal();
        } else {
            contestVOList = contestCacheManager.getContestVOList(contestQueryRequest, null);
            total = contestCacheManager.getListSize(contestQueryRequest.getType(), null);
        }
        if (CollectionUtil.isEmpty(contestVOList)) {
            return TableData.empty();
        }
        assembleContestVOList(contestVOList);
        return TableData.success(contestVOList, total);
    }

    @Override
    public TableData rankList(ContestRankRequest contestRankRequest) {
        Long total = contestCacheManager.getRankListSize(contestRankRequest.getContestId());
        List<ContestRankVO> contestRankVOS;
        if (total == null || total <= 0) {
            PageHelper.startPage(contestRankRequest.getPageNum(), contestRankRequest.getPageSize());
            contestRankVOS = userContestMapper.selectContestRankList(contestRankRequest.getContestId());
            contestCacheManager.refreshContestRankCache(contestRankRequest.getContestId());
            total = new PageInfo<>(contestRankVOS).getTotal();
        } else {
            contestRankVOS = contestCacheManager.getContestRankList(contestRankRequest);
        }
        if (CollectionUtil.isEmpty(contestRankVOS)) {
            return TableData.empty();
        }
        assembleContestRankVOList(contestRankVOS);
        return TableData.success(contestRankVOS, total);
    }

    @Override
    public String getFirstQuestion(Long contestId) {
        checkAndRefresh(contestId);
        return contestCacheManager.getFirstQuestion(contestId).toString();
    }

    @Override
    public String preQuestion(Long contestId, Long questionId) {
        checkAndRefresh(contestId);
        return contestCacheManager.preQuestion(contestId, questionId).toString();
    }

    @Override
    public String nextQuestion(Long contestId, Long questionId) {
        checkAndRefresh(contestId);
        return contestCacheManager.nextQuestion(contestId, questionId).toString();
    }

    private void assembleContestRankVOList(List<ContestRankVO> contestRankVOList) {
        if (CollectionUtil.isEmpty(contestRankVOList)) return;

        for (ContestRankVO contestRankVO : contestRankVOList) {
            Long userId = contestRankVO.getUserId();
            UserVO user = userCacheManager.getUserById(userId);
            contestRankVO.setNickName(user.getNickName());
        }
    }

    private void assembleContestVOList(List<ContestVO> contestVOList) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        List<Long> userContestList = contestCacheManager.getAllUserContestList(userId);
        if (CollectionUtil.isEmpty(userContestList)) {
            return;
        }
        for (ContestVO contestVO : contestVOList) {
            if (userContestList.contains(contestVO.getContestId())) {
                contestVO.setEnter(true);
            }
        }
    }

    private void checkAndRefresh(Long contestId) {
        Long listSize = contestCacheManager.getContestQuestionListSize(contestId);
        if (listSize == null || listSize <= 0) {
            contestCacheManager.refreshContestQuestionCache(contestId);
        }
    }
}
