package io.github.zhc.dev.friend.service.user;

import io.github.zhc.dev.common.core.model.entity.LoginUserVO;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.friend.model.dto.user.UserRequest;
import io.github.zhc.dev.friend.model.vo.user.UserVO;

/**
 * @author zhc.dev
 * @date 2025/4/5 23:17
 */
public interface UserService {
    boolean sendCode(UserRequest userRequest);

    String codeLogin(String email, String code);

    boolean logout(String token);

    R<LoginUserVO> info(String token);

    UserVO detail();
}
