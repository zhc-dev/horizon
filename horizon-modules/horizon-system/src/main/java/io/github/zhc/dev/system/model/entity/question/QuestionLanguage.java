package io.github.zhc.dev.system.model.entity.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/2 22:08
 */
@Getter
@Setter
@TableName("tb_question_language")
public class QuestionLanguage extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long questionLanguageId;
    private Long questionId;
    private Long languageId;
    private Integer timeLimit;
    private Integer spaceLimit;
    private String defaultCode;
    private String mainFunc;
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;
}
