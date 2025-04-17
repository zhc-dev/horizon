package io.github.zhc.dev.common.core.utils;

import io.github.zhc.dev.common.core.constants.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt 工具类
 *
 * @author zhc.dev
 * @date 2025/3/27 21：51
 */
@Slf4j
public class JwtUtils {
    /**
     * 生成令牌
     *
     * @param claims 数据
     * @param secret 密钥
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从令牌中获取数据
     *
     * @param token  令牌
     * @param secret 密钥
     * @return 数据
     */
    public static Claims parseToken(String token, String secret) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 获取user_id
     *
     * @param claims 数据
     * @return 用户Id
     */
    public static Long getUserId(Claims claims) {
        Object userIdObject = claims.getOrDefault(JwtConstant.USER_ID, 0L);
        if (userIdObject instanceof Integer) {
            return ((Integer) userIdObject).longValue();
        }
        return (Long) claims.getOrDefault(JwtConstant.USER_ID, 0L);
    }

    /**
     * 解析token，获取claims对象
     *
     * @param token  jwt令牌
     * @param secret jwt秘钥
     * @return claims
     */
    public static Claims getClaims(String token, String secret) {
        Claims claims;
        try {
            claims = JwtUtils.parseToken(token, secret);
            if (claims == null) {
                log.error("解析token：{}, 出现异常", token);
                return null;
            }
        } catch (Exception e) {
            log.error("解析token：{}, 出现异常", token, e);
            return null;
        }
        return claims;
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "zhc.dev");
        System.out.println(createToken(claims, "W2hvcml6b25daW8uZ2l0aHViLnpoYy5kZXYuaG9yaXpvbi5zZWN1cml0eS5qd3Quc2VjcmV0LmJhc2U2NC5vcmlnbmFs"));
    }
}