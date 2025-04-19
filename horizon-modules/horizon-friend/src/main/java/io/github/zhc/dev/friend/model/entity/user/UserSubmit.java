package io.github.zhc.dev.friend.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/19 17:37
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user_submit")
public class UserSubmit {
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

    private String caseJudgeRes;
}
