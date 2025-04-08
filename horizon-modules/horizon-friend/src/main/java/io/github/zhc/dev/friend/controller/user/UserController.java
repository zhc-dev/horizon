package io.github.zhc.dev.friend.controller.user;

import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.LoginUserVO;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.friend.model.dto.user.UserRequest;
import io.github.zhc.dev.friend.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/code/login")
    public R<String> codeLogin(@RequestBody UserRequest userRequest) {
        return R.ok(userService.codeLogin(userRequest.getEmail(), userRequest.getCode()));
    }

    @DeleteMapping("/logout")
    public R<Void> logout(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return toR(userService.logout(token));
    }

    @GetMapping("/info")
    public R<LoginUserVO> info(@RequestHeader(HttpConstants.AUTHENTICATION) String token) {
        return userService.info(token);
    }
}
