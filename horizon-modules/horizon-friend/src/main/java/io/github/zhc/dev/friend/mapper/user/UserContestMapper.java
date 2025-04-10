package io.github.zhc.dev.friend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.friend.model.entity.user.UserContest;
import io.github.zhc.dev.friend.model.vo.contest.ContestRankVO;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/9 20:47
 */
public interface UserContestMapper extends BaseMapper<UserContest> {
    List<ContestVO> selectUserContestList(Long userId);

    List<ContestRankVO> selectContestRankList(Long contestId);
}
