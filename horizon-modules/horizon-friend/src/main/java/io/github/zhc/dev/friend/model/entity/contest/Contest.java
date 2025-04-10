package io.github.zhc.dev.friend.model.entity.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhc-dev
 * @data 2025/4/10 20:59
 */
@Getter
@Setter
public class Contest extends BaseEntity {
    @TableId(value = "CONTEST_ID", type = IdType.ASSIGN_ID)
    private Long contestId;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;
}
