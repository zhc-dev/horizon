package io.github.zhc.dev.friend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:21
 */
@Getter
@Setter
public class UserRequest {
    private String email;
    private String code;
}
