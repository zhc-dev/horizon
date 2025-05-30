package io.github.zhc.dev.system.model.entity.system.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhc.dev
 * @date 2025/3/25 22:35
 */
@Getter
@Setter
@ToString
@TableName("tb_system_user")
public class SystemUser extends BaseEntity implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    private String userAccount;
    private String userPassword;
    private String nickName;
    private Integer grade;
    private Integer active;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        System.out.println(new SystemUser().getUserId());
    }
}