package io.github.zhc.dev.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 接口文档配置
 *
 * @author zhc.dev
 * @date 2025/3/26 21:01
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("horizon")
                        .description("horizon接⼝⽂档")
                        .version("v1"));
    }
}