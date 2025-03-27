package io.github.zhc.dev.common.core.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author zhc.dev
 * @date 2025/3/27 22:26
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    ORDINARY(1, "普通用户"),
    ADMIN(2, "管理员");

    private final Integer value;

    private final String desc;

}
