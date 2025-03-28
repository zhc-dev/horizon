package io.github.zhc.dev.security.config;

import io.github.zhc.dev.security.intercepter.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc 配置
 *
 * @author zhc.dev
 * @date 2025/3/28 16:11
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/**/login")
                .addPathPatterns("/**");
    }
}
