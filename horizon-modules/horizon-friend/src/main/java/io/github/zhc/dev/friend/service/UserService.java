package io.github.zhc.dev.friend.service;

import io.github.zhc.dev.friend.model.dto.UserRequest;
import org.springframework.stereotype.Service;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:17
 */
public interface UserService {
    boolean sendCode(UserRequest userRequest);
}
