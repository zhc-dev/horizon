package io.github.zhc.dev.system.model.entity.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhc.dev
 * @date 2025/4/2 22:13
 */
@Getter
@Setter
@TableName("tb_contest")
public class Contest extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long contestId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String allowedLanguages;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}
