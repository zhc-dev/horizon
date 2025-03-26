package io.github.zhc.common.core.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:26
 */
@Getter
@Setter
public class R<T> {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应描述
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
}