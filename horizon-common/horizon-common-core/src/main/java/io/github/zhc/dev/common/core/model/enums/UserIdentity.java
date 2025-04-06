package io.github.zhc.dev.common.core.model.enums;

import lombok.Getter;

/**
 * @author zhc.dev
 * @date 2025/4/6 18:28
 */
@Getter
public enum UserIdentity {
    ORDINARY(1, "普通用户"),

    ADMIN(2, "管理员");

    private Integer value;

    private String des;

    UserIdentity(Integer value, String des) {
        this.value = value;
        this.des = des;
    }
}
