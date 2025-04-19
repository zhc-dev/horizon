package io.github.zhc.dev.common.core.constants;

/**
 * HTTP 常量类，用于定义系统中常用的 HTTP 相关常量。
 *
 * @author zhc.dev
 * @date 2025/3/27 23:52
 */
public interface HttpConstants {
    /**
     * 服务端url标识
     */
    String SYSTEM_URL_PREFIX = "horizon-system";

    /**
     * 用户端url标识
     */
    String FRIEND_URL_PREFIX = "horizon-friend";

    /**
     * 令牌自定义标识
     */
    String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";
}