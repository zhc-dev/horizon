package io.github.zhc.dev.system.model.dto.user;

import io.github.zhc.dev.common.core.model.entity.PageQueryRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:39
 */
@Getter
@Setter
public class UserQueryRequest extends PageQueryRequest {
    private Long userId;
    private String nickName;
}
