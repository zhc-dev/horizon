package io.github.zhc.dev.system.model.vo.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:41
 */
@Getter
@Setter
public class UserVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String nickName;

    private Integer sex;

    private String phone;

    private String email;

    private String wechat;

    private String schoolName;

    private String majorName;

    private String introduce;

    private Integer status;
}
