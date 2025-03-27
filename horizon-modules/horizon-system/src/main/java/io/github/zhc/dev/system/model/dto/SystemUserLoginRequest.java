package io.github.zhc.dev.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 19:27
 */
@Getter
@Setter
public class SystemUserLoginRequest {
    @Schema(description = "用户账号")
    private String userAccount;
    @Schema(description = "用户密码")
    private String userPassword;

}
