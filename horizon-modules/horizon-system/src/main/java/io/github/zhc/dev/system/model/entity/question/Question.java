package io.github.zhc.dev.system.model.entity.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhc.dev.common.core.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 题目实体
 *
 * @author zhc.dev
 * @date 2025/3/30 22:37
 */
@TableName("tb_question")
@Getter
@Setter
public class Question extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long questionId;
    private String title;
    private Integer difficulty;
    private String content;
    private String tags;
    private String source;
    private String hint;
    @TableLogic(value = "0", delval = "1")
    private String isDeleted;
}