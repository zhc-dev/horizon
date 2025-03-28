package io.github.zhc.dev.security.exception;

import io.github.zhc.dev.common.core.model.enums.ResultCode;
import lombok.Getter;

/**
 * Service 异常
 *
 * @author zhc.dev
 * @date 2025/3/28 18:27
 */
@Getter
public class ServiceException extends RuntimeException {

    private final ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}