package io.github.zhc.dev.friend.model.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/15 22:07
 */
@Getter
@Setter
public class UserUpdateRequest {

    private String headImage;

    private String nickName;

    private Integer sex;

    private String schoolName;

    private String majorName;

    private String phone;

    private String email;

    private String wechat;

    private String introduce;
}
