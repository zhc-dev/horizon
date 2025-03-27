package io.github.zhc.dev.system.service.system.user.impl.system.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.common.core.model.enums.UserRole;
import io.github.zhc.dev.security.service.TokenService;
import io.github.zhc.dev.system.mapper.SystemUserMapper;
import io.github.zhc.dev.system.model.entity.SystemUser;
import io.github.zhc.dev.system.model.vo.SystemUserLoginVO;
import io.github.zhc.dev.system.service.system.user.impl.system.user.SystemUserService;
import io.github.zhc.dev.system.utils.utils.BCryptUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:11
 */
@Service
@RefreshScope
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Resource
    private TokenService tokenService;


    /**
     * 管理后台(系统用户)登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return 登录成功返回系统用户的视图，否则返回
     */
    @Override
    public R<SystemUserLoginVO> login(String userAccount, String userPassword) {
        // 查询数据库
        SystemUser systemUser = systemUserMapper.selectOne(new LambdaQueryWrapper<SystemUser>().// limit 1
                select(SystemUser::getUserId, SystemUser::getUserPassword). // select user_id,user_password
                eq(SystemUser::getUserAccount, userAccount)); // where user_account = ${userAccount}

        if (systemUser == null) return R.fail(ResultCode.FAILED_USER_NOT_EXISTS);

        if (StringUtils.isBlank(systemUser.getUserPassword())) return R.fail(ResultCode.FAILED_LOGIN);

        if (!BCryptUtils.matches(userPassword, systemUser.getUserPassword())) return R.fail(ResultCode.FAILED_LOGIN);

        // 登录成功!返回脱敏系统用户
        SystemUserLoginVO systemUserLoginVO = new SystemUserLoginVO();
        BeanUtils.copyProperties(systemUser, systemUserLoginVO);
        // 将令牌返回到前端
        systemUserLoginVO.setToken(tokenService.createToken(systemUser.getUserId(), secret, UserRole.ADMIN.getValue()));

        return R.ok(systemUserLoginVO);
    }
}
