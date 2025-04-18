package io.github.zhc.dev.friend.model.entity.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/18 20:55
 */
@Getter
@Setter
@TableName("tb_question_case")
public class QuestionCase extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long caseId;
    private Long questionId;
    private String input;
    private String output;
    private Integer isSample;
    private Integer score;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}
