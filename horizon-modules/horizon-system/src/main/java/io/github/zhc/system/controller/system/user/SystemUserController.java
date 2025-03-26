package io.github.zhc.system.controller.system.user;

import io.github.zhc.common.core.model.entity.R;
import io.github.zhc.common.core.model.enums.ResultCode;
import io.github.zhc.system.model.dto.SystemUserLoginRequest;
import io.github.zhc.system.model.dto.SystemUserRegisterRequest;
import io.github.zhc.system.model.vo.SystemUserLoginVO;
import io.github.zhc.system.service.system.user.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "管理员登录", description = "根据账号密码进行管理员登录")
    @ApiResponse(responseCode = "1000", description = "操作成功")
    @ApiResponse(responseCode = "2000", description = "服务繁忙请稍后重试")
    @ApiResponse(responseCode = "3006", description = "请求参数为空")
    @ApiResponse(responseCode = "3007", description = "请求参数包含空串")
    @ApiResponse(responseCode = "3008", description = "请求参数长度错误")
    @ApiResponse(responseCode = "3102", description = "用户不存在")
    @ApiResponse(responseCode = "3103", description = "用户名或密码错误")
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

    /**
     * 注册接口
     *
     * @param userRegisterRequest 系统用户注册DTO
     * @return 注册系统用户
     */
    @PostMapping("/register")
    public R<Void> register(@RequestBody SystemUserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) return R.fail(ResultCode.FAILED_PARAMS_NULL_ERROR);

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
            return R.fail(ResultCode.FAILED_PARAMS_EMPTY_ERROR);

        if (userAccount.length() < 4 || userAccount.length() > 30 || userPassword.length() < 8 || userPassword.length() > 64)
            return R.fail(ResultCode.FAILED_PARAMS_LENGTH_ERROR);

        if (!userPassword.equals(checkPassword)) return R.fail(ResultCode.FAILED_CHECK_PASSWORD_ERROR);

        return null;
    }
}










