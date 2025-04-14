package io.github.zhc.dev.friend.model.entity.question.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:28
 */
@Getter
@Setter
@Document(indexName = "idx_question_language")
public class QuestionLanguageES {
    @Id
    @Field(type = FieldType.Long)
    private Long questionLanguageId;

    @Field(type = FieldType.Long)
    private Long questionId;

    @Field(type = FieldType.Long)
    private Long languageId;

    @Field(type = FieldType.Integer)
    private Integer timeLimit;

    @Field(type = FieldType.Integer)
    private Integer spaceLimit;

    @Field(type = FieldType.Text)
    private String defaultCode;

    @Field(type = FieldType.Text)
    private String mainFunc;
}
