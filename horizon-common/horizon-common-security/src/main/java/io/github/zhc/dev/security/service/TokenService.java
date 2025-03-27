package io.github.zhc.dev.security.service;

import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.JwtConstant;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.common.core.model.entity.LoginUser;
import io.github.zhc.dev.common.core.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 令牌服务 操作Token
 *
 * @author zhc.dev
 * @date 2025/3/27 22:31
 */
@Service
public class TokenService {

    @Resource
    private RedisService redisService;

    public String createToken(Long userId, String secret, Integer userRole) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.USER_ID, userId);
        // 生成token
        String token = JwtUtils.createToken(claims, secret);
        // redis key
        String key = CacheConstants.LOGIN_KEY_PREFIX + userId;
        // redis value
        LoginUser loginUser = new LoginUser();
        loginUser.setRole(userRole);
        // put cache
        redisService.setCacheObject(key, loginUser, CacheConstants.LOGIN_EXPIRE, TimeUnit.MINUTES);
        return token;
    }
}
