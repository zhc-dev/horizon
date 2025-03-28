package io.github.zhc.dev.security.intercepter;

import cn.hutool.core.util.StrUtil;
import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.utils.JwtUtils;
import io.github.zhc.dev.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * token拦截器，恢复token有效时间到默认值
 *
 * @author zhc.dev
 * @date 2025/3/28 16:11
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Value("${jwt.secret}")
    private String secret;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);  //请求头中获取token
        if (StrUtil.isEmpty(token)) return true;

        Claims claims = JwtUtils.getClaims(token, secret);
        tokenService.resumeTokenDefaultExpire(claims);
        return true;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpConstants.AUTHENTICATION);
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, "");
        }
        return token;
    }
}
