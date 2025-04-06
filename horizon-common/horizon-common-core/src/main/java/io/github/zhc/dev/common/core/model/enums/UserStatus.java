package io.github.zhc.dev.common.core.model.enums;

/**
 * @author zhc.dev
 * @date 2025/4/6 18:26
 */
public enum UserStatus {
    Block(0),

    Normal(1);


    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
