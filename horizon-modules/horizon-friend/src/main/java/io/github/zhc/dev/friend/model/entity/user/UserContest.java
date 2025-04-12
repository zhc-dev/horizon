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
 * @date 2025/4/9 20:52
 */
@Getter
@Setter
@TableName("tb_user_contest")
public class UserContest extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "USER_CONTEST_ID", type = IdType.ASSIGN_ID)
    private Long userContestId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long contestId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private Integer score;

    private Integer contestRank;
}
