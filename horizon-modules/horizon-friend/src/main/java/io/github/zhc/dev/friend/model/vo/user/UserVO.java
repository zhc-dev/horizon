package io.github.zhc.dev.friend.model.vo.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:43
 */
@Getter
@Setter
public class UserVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String nickName;

    private String headImage;

    private Integer sex;

    private String phone;

    private String code;

    private String email;

    private String wechat;

    private String schoolName;

    private String majorName;

    private String introduce;

    private Integer status;
}
