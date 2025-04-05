package io.github.zhc.dev.system.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.system.model.dto.user.UserQueryRequest;
import io.github.zhc.dev.system.model.entity.user.User;
import io.github.zhc.dev.system.model.vo.user.UserVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:34
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserVO> selectUserList(UserQueryRequest userQueryRequest);
}
