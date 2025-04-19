package io.github.zhc.dev.friend.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.friend.model.entity.user.UserSubmit;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/19 17:39
 */
public interface UserSubmitMapper extends BaseMapper<UserSubmit> {
    UserSubmit selectCurrentUserSubmit(Long userId, Long contestId, Long questionId, String currentTime);

    List<Long> selectHostQuestionList();
}
