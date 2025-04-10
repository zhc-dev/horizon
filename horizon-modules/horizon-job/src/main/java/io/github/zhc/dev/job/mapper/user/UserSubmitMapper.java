package io.github.zhc.dev.job.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.job.model.entity.user.UserScore;
import io.github.zhc.dev.job.model.entity.user.UserSubmit;

import java.util.List;
import java.util.Set;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:57
 */
public interface UserSubmitMapper extends BaseMapper<UserSubmit> {
    List<UserScore> selectUserScoreList(Set<Long> contestIdSet);

    List<Long> selectHostQuestionList();
}
