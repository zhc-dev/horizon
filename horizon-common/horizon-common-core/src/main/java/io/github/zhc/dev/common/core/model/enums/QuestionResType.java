package io.github.zhc.dev.common.core.model.enums;

import lombok.Getter;

/**
 * @author zhc-dev
 * @data 2025/4/21 22:05
 */
@Getter
public enum QuestionResType {

    ERROR(0), //未通过

    PASS(1), //通过

    UN_SUBMIT(2),  //未提交

    IN_JUDGE(3); //  系统判题中

    private Integer value;

    QuestionResType(Integer value) {
        this.value = value;
    }
}