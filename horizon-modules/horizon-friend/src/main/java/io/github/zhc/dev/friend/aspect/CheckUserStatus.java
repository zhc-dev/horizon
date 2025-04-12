package io.github.zhc.dev.friend.aspect;

import java.lang.annotation.*;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckUserStatus {
}
