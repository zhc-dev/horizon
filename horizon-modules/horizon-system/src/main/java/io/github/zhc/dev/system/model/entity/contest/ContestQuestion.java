package io.github.zhc.dev.system.model.entity.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/2 22:15
 */
@Getter
@Setter
@TableName("tb_contest_question")
public class ContestQuestion extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long contestQuestionId;
    private Long contestId;
    private Long questionId;
    private Integer displayOrder;
    private String displayTitle;
    private Integer score;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}
