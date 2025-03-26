package io.github.zhc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.system.mapper.SystemUserMapper;
import io.github.zhc.system.model.entity.SystemUser;
import io.github.zhc.system.model.vo.SystemUserLoginVO;
import io.github.zhc.system.service.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author zhc.dev
 * @date 2025/3/26 16:11
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public SystemUserLoginVO login(String userAccount, String userPassword) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();

        SystemUser systemUser = systemUserMapper.selectOne(queryWrapper.
                select(SystemUser::getUserPassword).
                eq(SystemUser::getUserAccount, userAccount).
                eq(SystemUser::getUserPassword, userPassword));

        SystemUserLoginVO slu = new SystemUserLoginVO();
        if (systemUser == null) {
            slu.setCode(0);
            slu.setMsg("当前用户不存在");
            return slu;
        }

        if (systemUser.getUserPassword().equals(userPassword)) {
            slu.setCode(1);
            return slu;
        }
        slu.setCode(0);
        slu.setMsg("账号或密码错误");
        return slu;
    }
}
