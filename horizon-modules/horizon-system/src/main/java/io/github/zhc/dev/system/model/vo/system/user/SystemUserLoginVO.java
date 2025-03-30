package io.github.zhc.dev.system.model.vo.system.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:08
 */
@Getter
@Setter
public class SystemUserLoginVO {
    @Schema(description = "鉴权令牌")
    private String token;
}