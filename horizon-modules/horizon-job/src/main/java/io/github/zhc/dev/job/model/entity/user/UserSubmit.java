package io.github.zhc.dev.job.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:52
 */
@Getter
@Setter
@TableName("tb_user_submit")
public class UserSubmit extends BaseEntity {
    @TableId(value = "SUBMIT_ID", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long submitId;

    private Long userId;

    private Long questionId;

    private Long contestId;

    private Integer programType;

    private String userCode;

    private Integer pass;

    private Integer score;

    private String exeMessage;
}
