package io.github.zhc.dev.common.core.constants;

/**
 * 缓存常量
 *
 * @author zhc.dev
 * @date 2025/3/27 22:23
 */
public interface CacheConstants {
    String JWT_PAYLOAD_REDIS_KEY_PREFIX = "auth:jwt:payload:user_id:";
    Long JWT_TOKEN_DEFAULT_EXPIRATION_MINUTES = 720L;
    Long JWT_TOKEN_REFRESH_THRESHOLD_MINUTES = 180L;
}
