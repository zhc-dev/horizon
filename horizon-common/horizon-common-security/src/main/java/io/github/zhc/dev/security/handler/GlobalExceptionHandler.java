package io.github.zhc.dev.security.handler;


import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zhc.dev
 * @date 2025/3/26 22：57
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 请求⽅式不⽀持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?>
    handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求地址'{}',不⽀持'{}'请求", request.getRequestURI(), e.getMethod());
        return R.fail(ResultCode.ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public R<?> handleServiceException(ServiceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        ResultCode resultCode = e.getResultCode();
        log.error("请求地址'{}',发生业务异常: {}", requestURI, resultCode.getMsg(), e);
        return R.fail(resultCode);
    }


    /**
     * 拦截运⾏时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址'{}',发⽣异常.", request.getRequestURI(), e);
        return R.fail(ResultCode.ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址'{}',发⽣异常.", request.getRequestURI(), e);
        return R.fail(ResultCode.ERROR);
    }
}