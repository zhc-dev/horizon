package io.github.zhc.system.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/26 19:27
 */
@Getter
@Setter
public class SystemUserLoginRequest {
    private String userAccount;
    private String userPassword;

}
