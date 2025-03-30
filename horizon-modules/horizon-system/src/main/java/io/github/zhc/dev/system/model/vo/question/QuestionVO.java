package io.github.zhc.dev.system.model.vo.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhc.dev
 * @date 2025/3/30 23:07
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionVO {
    @Schema(description = "题目ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    @Schema(description = "题目标题")
    private String title;

    @Schema(description = "题目难度")
    private Integer difficulty;

    @Schema(description = "创建人")
    private String createName;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}