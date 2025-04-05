package io.github.zhc.dev.system.service.impl.user;

import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.system.manager.UserCacheManager;
import io.github.zhc.dev.system.mapper.user.UserMapper;
import io.github.zhc.dev.system.model.dto.user.UserQueryRequest;
import io.github.zhc.dev.system.model.dto.user.UserRequest;
import io.github.zhc.dev.system.model.entity.user.User;
import io.github.zhc.dev.system.model.vo.user.UserVO;
import io.github.zhc.dev.system.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:36
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCacheManager userCacheManager;

    @Override
    public List<UserVO> list(UserQueryRequest userQueryRequest) {
        PageHelper.startPage(userQueryRequest.getPageNum(), userQueryRequest.getPageSize());
        return userMapper.selectUserList(userQueryRequest);
    }

    @Override
    public int updateStatus(UserRequest userRequest) {
        User user = userMapper.selectById(userRequest.getUserId());
        if (user == null) {
            throw new ServiceException(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        user.setStatus(userRequest.getStatus());
        userCacheManager.updateStatus(user.getUserId(), userRequest.getStatus());
        return userMapper.updateById(user);
    }
}