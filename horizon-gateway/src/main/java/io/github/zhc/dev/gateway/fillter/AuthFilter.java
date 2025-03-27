package io.github.zhc.dev.gateway.fillter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.model.entity.LoginUser;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.common.core.model.enums.UserRole;
import io.github.zhc.dev.gateway.properties.IgnoreWhiteProperties;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.common.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * spring cloud gateway 鉴权
 * Spring Cloud Gateway 是基于 Spring WebFlux（响应式框架）构建的
 * Spring MVC（基于 Servlet 的阻塞式框架）与 WebFlux 存在冲突。
 * 需要在网关组件内，排除Spring MVC 的依赖
 *
 * @author zhc.dev
 * @date 2025/3/27 22:37
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    // 排除过滤的 uri 白名单地址，在nacos自行添加
    @Resource
    private IgnoreWhiteProperties ignoreWhite;

    @Value("${jwt.secret}")
    private String secret;

    @Resource
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        // 白名单
        if (matches(url, ignoreWhite.getWhites())) return chain.filter(exchange);

        String token = getToken(request);
        if (StrUtil.isEmpty(token)) return unauthorizedResponse(exchange, "令牌不能为空");

        Claims claims;
        try {
            claims = JwtUtils.parseToken(token, secret);
            if (claims == null) return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        } catch (Exception e) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }

        //通过redis中存储的数据，来控制jwt的过期时间
        String userId = JwtUtils.getUserId(claims);

        if (!redisService.hasKey(getTokenKey(userId))) return unauthorizedResponse(exchange, "登录状态已过期");

        //判断jwt中的信息是否完整
        if (StrUtil.isEmpty(userId)) return unauthorizedResponse(exchange, "令牌验证失败");

        // B端仅管理员访问
        LoginUser user = redisService.getCacheObject(getTokenKey(userId), LoginUser.class);

        if (url.contains(HttpConstants.SYSTEM_URL_PREFIX) && !UserRole.ADMIN.getValue().equals(user.getRole()))
            return unauthorizedResponse(exchange, "令牌验证失败");

        if (url.contains(HttpConstants.FRIEND_URL_PREFIX) && !UserRole.ORDINARY.getValue().equals(user.getRole()))
            return unauthorizedResponse(exchange, "令牌验证失败");


        return chain.filter(exchange);
    }

    /**
     * 查找指定url是否匹配指定匹配规则链表中的任意一个字符串
     *
     * @param url         指定url
     * @param patternList 需要检查的匹配规则链表
     * @return 是否匹配
     */
    private boolean matches(String url, List<String> patternList) {
        if (StrUtil.isEmpty(url) || CollectionUtils.isEmpty(patternList)) {
            return false;
        }
        for (String pattern : patternList) {
            if (isMatch(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则匹配
     * 匹配规则中：
     * pattern 中可以写一些特殊字符
     * ? 表示单个任意字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return 是否匹配
     */
    private boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_KEY_PREFIX + token;
    }

    /**
     * 从请求头中获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(HttpConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, StrUtil.EMPTY);
        }
        return token;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return webFluxResponseWriter(exchange.getResponse(), msg, ResultCode.FAILED_UNAUTHORIZED.getCode());
    }

    //拼装webflux模型响应
    private Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String msg, int code) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        R<?> result = R.fail(code, msg);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    /**
     * @return 过滤器优先级值越小 过滤器就越先被执行
     */
    @Override
    public int getOrder() {
        return -200;
    }
}