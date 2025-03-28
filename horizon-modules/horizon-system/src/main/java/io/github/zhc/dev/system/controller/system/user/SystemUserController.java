package io.github.zhc.dev.system.controller.system.user;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.system.model.dto.SystemUserLoginRequest;
import io.github.zhc.dev.system.model.dto.SystemUserAddRequest;
import io.github.zhc.dev.system.model.vo.SystemUserLoginVO;
import io.github.zhc.dev.system.service.system.user.impl.system.user.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
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
public class SystemUserController extends BaseController {

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
    public R<SystemUserLoginVO> login(@Validated @RequestBody @NotNull SystemUserLoginRequest userLoginRequest) {

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        return systemUserService.login(userAccount, userPassword);
    }

    /**
     * 预置数据接口
     *
     * @param systemUserAddRequest 系统用户
     */
    @PostMapping("/add")
    @Operation(summary = "新增管理员", description = "根据提供的信息新增管理员")
    @ApiResponse(responseCode = "1000", description = "操作成功")
    @ApiResponse(responseCode = "2000", description = "服务繁忙请稍后重试")
    @ApiResponse(responseCode = "3101", description = "用户已存在")
    public R<Void> add(@Validated @RequestBody @NotNull SystemUserAddRequest systemUserAddRequest) {
        String userAccount = systemUserAddRequest.getUserAccount();
        String userPassword = systemUserAddRequest.getUserPassword();
        String checkPassword = systemUserAddRequest.getCheckPassword();

        if (!userPassword.equals(checkPassword)) return R.fail(ResultCode.FAILED_PARAMS_VALIDATE);

        return toR(systemUserService.add(userAccount, userPassword));
    }


}










