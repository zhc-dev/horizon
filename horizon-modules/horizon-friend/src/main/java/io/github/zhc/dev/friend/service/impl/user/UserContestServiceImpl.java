package io.github.zhc.dev.friend.service.impl.user;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.common.core.model.enums.ContestListType;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.common.core.utils.ThreadLocalUtil;
import io.github.zhc.dev.friend.manager.ContestCacheManager;
import io.github.zhc.dev.friend.manager.UserCacheManager;
import io.github.zhc.dev.friend.mapper.contest.ContestMapper;
import io.github.zhc.dev.friend.mapper.user.UserContestMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.entity.contest.Contest;
import io.github.zhc.dev.friend.model.entity.user.UserContest;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;
import io.github.zhc.dev.friend.service.user.UserContestService;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.security.service.TokenService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:35
 */
@Service
public class UserContestServiceImpl implements UserContestService {

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private UserContestMapper userContestMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private ContestCacheManager contestCacheManager;

    @Value("${jwt.secret}")
    private String secret;

    @Resource
    private UserCacheManager userCacheManager;

    @Override
    public int enter(String token, Long contestId) {
        Contest contest = contestMapper.selectById(contestId);
        if (contest == null) {
            throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        }
        if(contest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.CONTEST_STARTED);
        }
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        UserContest userContest = userContestMapper.selectOne(new LambdaQueryWrapper<UserContest>()
                .eq(UserContest::getContestId, contestId)
                .eq(UserContest::getUserId, userId));
        if (userContest != null) {
            throw new ServiceException(ResultCode.USER_CONTEST_HAS_ENTER);
        }
        contestCacheManager.addUserContestCache(userId, contestId);
        userContest = new UserContest();
        userContest.setContestId(contestId);
        userContest.setUserId(userId);
        return userContestMapper.insert(userContest);
    }

    @Override
    public TableData list(ContestQueryRequest contestQueryRequest) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        contestQueryRequest.setType(ContestListType.USER_CONTEST_LIST.getValue());
        Long total = contestCacheManager.getListSize(ContestListType.USER_CONTEST_LIST.getValue(), userId);
        List<ContestVO> contestVOList;
        if (total == null || total <= 0) {
            //从数据库中查询我的竞赛列表
            PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
            contestVOList = userContestMapper.selectUserContestList(userId);
            contestCacheManager.refreshCache(ContestListType.USER_CONTEST_LIST.getValue(), userId);
            total = new PageInfo<>(contestVOList).getTotal();
        } else {
            contestVOList = contestCacheManager.getContestVOList(contestQueryRequest, userId);
            total = contestCacheManager.getListSize(contestQueryRequest.getType(), userId);
        }
        if (CollectionUtil.isEmpty(contestVOList)) {
            return TableData.empty();
        }
        return TableData.success(contestVOList, total);
    }
}
