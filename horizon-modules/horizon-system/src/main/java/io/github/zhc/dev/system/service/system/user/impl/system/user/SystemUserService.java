package io.github.zhc.dev.system.service.system.user.impl.system.user;

import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.system.model.vo.CurrentLoginUserVO;
import io.github.zhc.dev.system.model.vo.SystemUserLoginVO;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:07
 */
public interface SystemUserService {
    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return 用户vo
     */
    R<SystemUserLoginVO> login(String userAccount, String userPassword);

    /**
     * 新增系统用户
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return 数据库受影响的行数
     */
    int add(String userAccount, String userPassword);

    /**
     * 获取当前登录用户信息
     *
     * @param token 令牌
     * @return 当前登录用户信息
     */
    R<CurrentLoginUserVO> currentLoginUser(String token);
}
