package io.github.zhc.dev.file.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/16 21:09
 */
@Getter
@Setter
public class OSSResult {
    private String name;

    /**
     * 对象状态：true成功，false失败
     */
    private boolean success;
}
