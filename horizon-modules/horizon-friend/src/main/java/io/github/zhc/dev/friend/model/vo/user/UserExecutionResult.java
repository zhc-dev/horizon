package io.github.zhc.dev.friend.model.vo.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/21 22:08
 */
@Getter
@Setter
public class UserExecutionResult {
    private String input;
    private String expectedOutput;
    private String output;
}
