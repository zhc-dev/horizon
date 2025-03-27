package io.github.zhc.dev.system.service.system.user.impl.user;

import io.github.zhc.dev.common.core.entity.R;
import io.github.zhc.dev.system.model.vo.SystemUserLoginVO;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:07
 */
public interface SystemUserService {
    R<SystemUserLoginVO> login(String userAccount, String userPassword);
}
