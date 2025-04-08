package io.github.zhc.dev.security.service;

import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.JwtConstant;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.common.core.model.entity.LoginUserVO;
import io.github.zhc.dev.common.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class TokenService {

    @Resource
    private RedisService redisService;

    public String createToken(Long userId, String secret, String nickName, String headImage) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.USER_ID, userId);
        // 生成token
        String token = JwtUtils.createToken(claims, secret);
        // redis key
        String key = CacheConstants.JWT_PAYLOAD_REDIS_KEY_PREFIX + userId;
        // redis value
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setNickName(nickName);
        loginUserVO.setHeadImage(headImage);
        // put cache
        redisService.setCacheObject(key, loginUserVO, CacheConstants.JWT_TOKEN_DEFAULT_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 恢复Token有效时间到默认值
     *
     * @param claims claims 对象
     */
    public void resumeTokenDefaultExpire(Claims claims) {
        String userId = getUserId(claims);

        if (userId == null) return;

        String tokenKey = getJwtPayloadRedisKey(userId);

        Long expire = redisService.getExpire(tokenKey, TimeUnit.MINUTES);
        if (expire != null && expire < CacheConstants.JWT_TOKEN_REFRESH_THRESHOLD_MINUTES)
            redisService.expire(tokenKey, CacheConstants.JWT_TOKEN_DEFAULT_EXPIRATION_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * 获取jwt payload 中的用户身份标识
     *
     * @param claims claims对象
     * @return 用户id
     */
    public String getUserId(Claims claims) {
        if (claims == null) return null;
        return JwtUtils.getUserId(claims);
    }

    /**
     * 获取jwt payload 中的登录用户
     *
     * @param token  token
     * @param secret secret
     * @return 用户id
     */
    public LoginUserVO getLoginUser(String token, String secret) {
        String userId = getUserId(JwtUtils.getClaims(token, secret));
        if (userId == null) return null;
        return redisService.getCacheObject(getJwtPayloadRedisKey(userId), LoginUserVO.class);
    }

    /**
     * 删除登录用户
     *
     * @param token  token
     * @param secret secret
     */
    public boolean deleteLoginUser(String token, String secret) {
        String userId = getUserId(JwtUtils.getClaims(token, secret));
        if (userId == null) return false;
        return redisService.deleteObject(getJwtPayloadRedisKey(userId));
    }

    /**
     * 获取jwt key的完整名称
     *
     * @param userId 用户id
     * @return jwt redis key的完整名称
     */
    private String getJwtPayloadRedisKey(String userId) {
        return CacheConstants.JWT_PAYLOAD_REDIS_KEY_PREFIX + userId;
    }
}
