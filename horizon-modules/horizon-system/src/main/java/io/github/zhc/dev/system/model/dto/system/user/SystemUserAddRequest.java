package io.github.zhc.dev.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 21:10
 */
@Getter
@Setter
public class SystemUserAddRequest {
    @NotBlank(message = "账号不能仅包含空白字符")
    @Size(min = 4, max = 32, message = "账号长度需要在4-32之间")
    @Schema(description = "用户账号")
    private String userAccount;

    @NotBlank(message = "密码不能仅包含空白字符")
    @Size(min = 8, max = 64, message = "密码长度需要在8-64之间")
    @Schema(description = "用户密码")
    private String userPassword;

    @NotBlank(message = "确认密码与密码不同")
    @Size(min = 8, max = 64, message = "确认密码与密码不同")
    @Schema(description = "确认密码")
    private String checkPassword;
}
