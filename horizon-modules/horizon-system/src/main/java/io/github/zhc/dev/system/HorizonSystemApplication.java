package io.github.zhc.dev.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhc.dev
 * @date 2025/3/27 19:06
 */
@SpringBootApplication
@MapperScan("io.github.zhc.dev.system.**.mapper")
public class HorizonSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HorizonSystemApplication.class, args);
    }
}
