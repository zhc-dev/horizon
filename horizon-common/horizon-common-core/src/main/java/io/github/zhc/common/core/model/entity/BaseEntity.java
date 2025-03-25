package io.github.zhc.common.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类基类，用于追踪数据源（创建、更新历史），寻找相关责任人
 *
 * @author zhc.dev
 * @date 2025/3/25 22:48
 */
@Getter
@Setter
public class BaseEntity implements Serializable {
    private Long createBy;
    private Long updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}
