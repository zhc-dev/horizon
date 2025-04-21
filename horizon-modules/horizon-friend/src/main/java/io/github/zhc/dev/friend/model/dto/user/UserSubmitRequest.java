package io.github.zhc.dev.friend.model.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/21 22:13
 */
@Getter
@Setter
public class UserSubmitRequest {
    private Long contestId;
    private Long questionId;
    private Long languageId;
    private String code;
}
