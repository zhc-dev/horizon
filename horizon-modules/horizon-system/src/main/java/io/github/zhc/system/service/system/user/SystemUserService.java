package io.github.zhc.system.service;

import io.github.zhc.system.model.vo.SystemUserLoginVO;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:07
 */
public interface SystemUserService {
    SystemUserLoginVO login(String userAccount, String userPassword);
}
