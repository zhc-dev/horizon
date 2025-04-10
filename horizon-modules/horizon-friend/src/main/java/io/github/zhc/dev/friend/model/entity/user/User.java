package io.github.zhc.dev.friend.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 22:34
 */
@Getter
@Setter
@TableName("tb_user")
public class User extends BaseEntity<BaseEntity> {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "USER_ID", type = IdType.ASSIGN_ID)
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
