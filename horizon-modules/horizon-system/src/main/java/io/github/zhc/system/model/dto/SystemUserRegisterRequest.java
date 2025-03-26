package io.github.zhc.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 21:10
 */
@Getter
@Setter
public class SystemUserRegisterRequest {
    @Schema(description = "用户账号")
    private String userAccount;
    @Schema(description = "用户密码")
    private String userPassword;
    @Schema(description = "确认密码")
    private String checkPassword;
}
