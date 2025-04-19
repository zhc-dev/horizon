package io.github.zhc.dev.api.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/20 00:05
 */
@Getter
@Setter
public class UserExeResult {
    private String input; // <- 输入

    private String output;   // <- 期望输出

    private String exeOutput; // <- 实际输出
}
