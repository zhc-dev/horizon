package io.github.zhc.dev.common.core.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/27 22:13
 */
@Getter
@Setter
public class LoginUser {
    // 1 -> 普通用户 2 -> 管理员
    private Integer role;
    private String nickName;
    private String headImage;
}
