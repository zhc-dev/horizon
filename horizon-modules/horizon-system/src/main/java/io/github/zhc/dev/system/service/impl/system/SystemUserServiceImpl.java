package io.github.zhc.dev.system.service.impl.system;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.model.entity.LoginUserVO;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.common.core.model.enums.UserRole;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.security.service.TokenService;
import io.github.zhc.dev.system.mapper.system.user.SystemUserMapper;
import io.github.zhc.dev.system.model.entity.system.user.SystemUser;
import io.github.zhc.dev.system.model.vo.system.user.CurrentLoginUserVO;
import io.github.zhc.dev.system.model.vo.system.user.SystemUserLoginVO;
import io.github.zhc.dev.system.service.system.user.SystemUserService;
import io.github.zhc.dev.system.utils.utils.BCryptUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

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
                select(SystemUser::getUserId, SystemUser::getUserPassword, SystemUser::getNickName). // select user_id,user_password
                eq(SystemUser::getUserAccount, userAccount)); // where user_account = ${userAccount}

        if (systemUser == null) return R.fail(ResultCode.FAILED_USER_NOT_EXISTS);

        if (StringUtils.isBlank(systemUser.getUserPassword())) return R.fail(ResultCode.FAILED_LOGIN);

        if (!BCryptUtils.matches(userPassword, systemUser.getUserPassword())) return R.fail(ResultCode.FAILED_LOGIN);

        // 登录成功!返回脱敏系统用户
        SystemUserLoginVO systemUserLoginVO = new SystemUserLoginVO();
        BeanUtils.copyProperties(systemUser, systemUserLoginVO);
        // 将令牌返回到前端
        systemUserLoginVO.setToken(tokenService.createToken(systemUser.getUserId(), secret, UserRole.ADMIN.getValue(), systemUser.getNickName(), null));

        return R.ok(systemUserLoginVO);
    }

    /**
     * 管理后台 退出登录
     *
     * @param token 令牌
     * @return 退出登录状态
     */
    @Override
    public boolean logout(String token) {
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, StrUtil.EMPTY);
        }
        return tokenService.deleteLoginUser(token, secret);
    }

    /**
     * 管理后台 获取当前登录用户
     *
     * @param token 令牌
     * @return 当前登录用户视图
     */
    @Override
    public R<CurrentLoginUserVO> currentLoginUser(String token) {
        if (StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, StrUtil.EMPTY);
        }
        // 查询redis，获取当前登录用户
        LoginUserVO loginUserVO = tokenService.getLoginUser(token, secret);
        if (loginUserVO == null) return R.fail();

        // 封装返回对象
        CurrentLoginUserVO currentLoginUserVO = new CurrentLoginUserVO();
        currentLoginUserVO.setNickName(loginUserVO.getNickName());

        return R.ok(currentLoginUserVO);
    }


    /**
     * 管理后台 预置数据
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @return 插入成功返回1，否则返回0
     */
    @Override
    public int add(String userAccount, String userPassword) {
        List<SystemUser> users = systemUserMapper.selectList(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUserAccount, userAccount));
        if (CollectionUtil.isNotEmpty(users)) throw new ServiceException(ResultCode.AILED_USER_EXISTS);

        SystemUser systemUser = new SystemUser();
        systemUser.setUserAccount(userAccount);
        systemUser.setUserPassword(BCryptUtils.encrypt(userPassword));

        return systemUserMapper.insert(systemUser);
    }
}
