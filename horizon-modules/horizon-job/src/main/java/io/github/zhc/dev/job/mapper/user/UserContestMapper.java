package io.github.zhc.dev.job.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.job.model.entity.user.UserContest;
import io.github.zhc.dev.job.model.entity.user.UserScore;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:57
 */
public interface UserContestMapper extends BaseMapper<UserContest> {
    void updateUserScoreAndRank(List<UserScore> userScoreList);
}
