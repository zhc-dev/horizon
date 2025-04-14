package io.github.zhc.dev.friend.model.entity.question.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:19
 */
@Document(indexName = "idx_question")
public class QuestionES {
    @Id
    @Field(type = FieldType.Long)
    private Long questionId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Byte)
    private Integer difficulty;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String tags;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String source;

    @Field(type = FieldType.Text)
    private String hint;

    @Field(type = FieldType.Nested)
    private List<QuestionCaseES> cases;

    @Field(type = FieldType.Nested)
    private List<QuestionLanguageES> languages;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createTime;
}
