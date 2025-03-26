package io.github.zhc.system.controller.systemuser;

import io.github.zhc.common.core.model.entity.R;
import io.github.zhc.system.model.dto.SystemUserLoginRequest;
import io.github.zhc.system.model.vo.SystemUserLoginVO;
import io.github.zhc.system.service.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * B端用户相关接口
 *
 * @author zhc.dev
 * @date 2025/3/26 16:03
 */
@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;


    /**
     * 登录接口
     *
     * @param userLoginRequest 系统用户登录DTO
     * @return
     */
    @PostMapping("/login")
    public R<SystemUserLoginVO> login(@RequestBody SystemUserLoginRequest userLoginRequest) {
        return systemUserService.login(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword());
    }
}
