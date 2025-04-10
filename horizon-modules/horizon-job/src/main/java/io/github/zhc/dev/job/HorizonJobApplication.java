package io.github.zhc.dev.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhc.dev
 * @date 2025/3/25 21:00
 */
@SpringBootApplication
@MapperScan("io.github.zhc.dev.job.**.mapper")
public class HorizonJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(HorizonJobApplication.class, args);
    }
}
