package io.github.zhc.dev.system.model.vo.system.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 当前登录用户信息
 *
 * @author zhc.dev
 * @date 2025/3/30 19:59
 */
@Getter
@Setter
public class CurrentLoginUserVO {
    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户角色")
    private int role;
}
