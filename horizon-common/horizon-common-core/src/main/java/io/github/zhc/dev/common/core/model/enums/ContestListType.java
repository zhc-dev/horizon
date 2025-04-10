package io.github.zhc.dev.common.core.model.enums;

import lombok.Getter;

/**
 * @author zhc-dev
 * @data 2025/4/10 21:04
 */
@Getter
public enum ContestListType {
    CONTEST_UNFINISHED_LIST(0),

    CONTEST_HISTORY_LIST(1),

    USER_CONTEST_LIST(2);

    private final Integer value;

    ContestListType(Integer value) {
        this.value = value;
    }
}
