package io.github.zhc.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhc.dev
 * @date 2025/3/25 21:01
 */
@MapperScan("io.github.zhc.system.**.mapper")
@SpringBootApplication
public class HorizonSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HorizonSystemApplication.class, args);
    }
}
