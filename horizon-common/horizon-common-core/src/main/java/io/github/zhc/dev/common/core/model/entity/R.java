package io.github.zhc.dev.common.core.model.entity;

import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    @Schema(description = "响应状态码")
    private int code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    public static <T> R<T> ok() {
        return assembleResult(null, ResultCode.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return assembleResult(data, ResultCode.SUCCESS);
    }

    public static <T> R<T> fail() {
        return assembleResult(null, ResultCode.FAILED);
    }

    public static <T> R<T> fail(int code, String msg) {
        return assembleResult(code, msg, null);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return assembleResult(null, resultCode);
    }

    private static <T> R<T> assembleResult(T data, ResultCode resultCode) {
        return new Builder<T>().code(resultCode.getCode()).msg(resultCode.getMsg()).data(data).build();
    }

    private static <T> R<T> assembleResult(int code, String msg, T data) {
        return new Builder<T>().code(code).msg(msg).data(data).build();
    }

    // builder
    public static class Builder<T> {
        private int code;
        private String msg;
        private T data;

        private Builder() {
        }

        public R<T> build() {
            return new R<>(code, msg, data);
        }

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }
    }
}