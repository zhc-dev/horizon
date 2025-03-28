package io.github.zhc.dev.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zhc.dev
 * @date 2025/3/27 18:55
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HorizonGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(HorizonGatewayApplication.class, args);
    }
}
