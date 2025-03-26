package io.github.zhc.system.service.impl;

import io.github.zhc.system.model.vo.LoginUserVO;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:07
 */
public interface SystemUserService {
    LoginUserVO login(String userAccount, String userPassword);
}
