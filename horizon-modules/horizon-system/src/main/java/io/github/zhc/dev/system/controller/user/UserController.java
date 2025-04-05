package io.github.zhc.dev.system.controller.user;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.model.dto.user.UserQueryRequest;
import io.github.zhc.dev.system.model.dto.user.UserRequest;
import io.github.zhc.dev.system.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:35
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;


    @GetMapping("/list")
    public TableData list(UserQueryRequest userQueryRequest) {
        return getTableData(userService.list(userQueryRequest));
    }

    @PutMapping("/update-status")
    public R<Void> updateStatus(@RequestBody UserRequest userRequest) {
        return toR(userService.updateStatus(userRequest));
    }

}
