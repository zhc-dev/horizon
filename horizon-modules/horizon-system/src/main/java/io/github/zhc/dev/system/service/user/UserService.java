package io.github.zhc.dev.system.service.user;

import io.github.zhc.dev.system.model.dto.user.UserQueryRequest;
import io.github.zhc.dev.system.model.dto.user.UserRequest;
import io.github.zhc.dev.system.model.vo.user.UserVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:36
 */
public interface UserService {
    List<UserVO> list(UserQueryRequest userQueryRequest);

    int updateStatus(UserRequest userRequest);
}
