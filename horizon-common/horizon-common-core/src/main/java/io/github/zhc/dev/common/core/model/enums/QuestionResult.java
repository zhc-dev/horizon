package io.github.zhc.dev.common.core.model.enums;

/**
 * @author zhc-dev
 * @data 2025/4/21 22:06
 */
public enum QuestionResult {
    ERROR(0),

    PASS(1);

    private Integer value;

    QuestionResult(Integer value) {
        this.value = value;
    }
}
