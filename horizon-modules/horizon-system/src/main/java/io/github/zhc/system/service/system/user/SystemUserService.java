package io.github.zhc.system.service.system.user;

import io.github.zhc.common.core.model.entity.R;
import io.github.zhc.system.model.vo.SystemUserLoginVO;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:07
 */
public interface SystemUserService {
    R<SystemUserLoginVO> login(String userAccount, String userPassword);
}
