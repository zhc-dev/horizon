package io.github.zhc.dev.friend.model.entity.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/9 20:44
 */
@Getter
@Setter
@TableName("tb_contest_question")
public class ContestQuestion {
    @TableId(value = "EXAM_QUESTION_ID", type = IdType.ASSIGN_ID)
    private Long contestQuestionId;

    private Long contestId;

    private Long questionId;

    private Integer displayOrder;
}
