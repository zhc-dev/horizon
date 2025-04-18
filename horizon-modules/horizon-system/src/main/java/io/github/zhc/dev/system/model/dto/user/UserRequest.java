package io.github.zhc.dev.system.model.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:40
 */
@Getter
@Setter
public class UserRequest {
    private Long userId;
    private Integer status;
}
