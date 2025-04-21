package io.github.zhc.dev.friend.model.vo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/21 22:09
 */
@Getter
@Setter
public class UserQuestionResultVO {
    private Integer result;
    private List<UserExecutionResult> executionResultList;
    private String errorMessage;
}
