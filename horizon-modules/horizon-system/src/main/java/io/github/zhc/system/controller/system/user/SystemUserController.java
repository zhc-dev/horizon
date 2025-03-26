package io.github.zhc.system.controller.system.user;

import io.github.zhc.common.core.model.entity.R;
import io.github.zhc.common.core.model.enums.ResultCode;
import io.github.zhc.system.model.dto.SystemUserLoginRequest;
import io.github.zhc.system.model.vo.SystemUserLoginVO;
import io.github.zhc.system.service.system.user.SystemUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
     * @return 脱敏用户数据
     */
    @PostMapping("/login")
    public R<SystemUserLoginVO> login(@RequestBody SystemUserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) return R.fail(ResultCode.FAILED_PARAMS_NULL_ERROR);

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) return R.fail(ResultCode.FAILED_PARAMS_EMPTY_ERROR);
        // 账号密码长度校验 符合要求的区间：账号 [4,30] 密码 [8,64]
        if (userAccount.length() < 4 || userAccount.length() > 30 || userPassword.length() < 8 || userPassword.length() > 64)
            return R.fail(ResultCode.FAILED_PARAMS_LENGTH_ERROR);

        return systemUserService.login(userAccount, userPassword);
    }
}










