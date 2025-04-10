package io.github.zhc.dev.job.model.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:47
 */
@Getter
@Setter
@TableName("tb_message")
public class Message extends BaseEntity {
    @TableId(value = "MESSAGE_ID", type = IdType.ASSIGN_ID)
    private Long messageId;

    private Long textId;

    private Long sendId;

    private Long recId;
}
