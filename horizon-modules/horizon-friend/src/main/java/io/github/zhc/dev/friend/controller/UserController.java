package io.github.zhc.dev.friend.controller;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.friend.model.dto.UserRequest;
import io.github.zhc.dev.friend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:15
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @PostMapping("/send-code")
    public R<Void> sendCode(@RequestBody UserRequest userRequest) {
        return toR(userService.sendCode(userRequest));
    }
}
