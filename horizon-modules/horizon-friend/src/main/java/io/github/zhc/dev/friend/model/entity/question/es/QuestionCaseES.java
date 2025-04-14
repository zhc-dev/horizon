package io.github.zhc.dev.friend.model.entity.question.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:27
 */
@Getter
@Setter
@Document(indexName = "idx_question_case")
public class QuestionCaseES {
    @Id
    @Field(type = FieldType.Long)
    private Long caseId;
    @Field(type = FieldType.Long)
    private Long questionId;
    @Field(type = FieldType.Text)
    private String input;
    @Field(type = FieldType.Text)
    private String output;
    @Field(type = FieldType.Byte)
    private Integer isSample;
    @Field(type = FieldType.Integer)
    private Integer score;
}
