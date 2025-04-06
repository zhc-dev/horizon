package io.github.zhc.dev.friend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhc.dev
 * @date 2025/3/25 20:59
 */
@SpringBootApplication
@MapperScan("io.github.zhc.dev.friend.mapper")
public class HorizonFriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(HorizonFriendApplication.class, args);
    }
}
