package io.github.zhc.dev.system.test.controller;

import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.system.model.entity.SystemUser;
import io.github.zhc.dev.system.test.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 整合Mybatis Plus测试
 *
 * @author zhc.dev
 * @date 2025/3/25 22:01
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestService testService;

    @Resource
    private RedisService redisService;

    // http://localhost:14168/test/list
    @GetMapping("/list")
    public List<?> list() {
        return testService.list();
    }


    @GetMapping("/redisAddAndGet")
    public String redisAddAndGet() {
        SystemUser user = new SystemUser();
        user.setUserAccount("admin");
        user.setUserPassword("admin@horizon");
        redisService.setCacheObject("user", user);
        return redisService.getCacheObject("user",SystemUser.class).toString();

    }
}
