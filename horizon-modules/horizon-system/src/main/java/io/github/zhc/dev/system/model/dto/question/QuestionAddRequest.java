package io.github.zhc.dev.system.model.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/3/31 22:32
 */
@Getter
@Setter
public class QuestionAddRequest {
    @Schema(description = "题目id")
    private Long questionId;

    @Schema(description = "题目标题")
    private String title;

    @Schema(description = "题目难度")
    private Integer difficulty;

    @Schema(description = "题目内容")
    private String content;

    @Schema(description = "标签列表")
    private String tags;

    @Schema(description = "题目来源")
    private String source;

    @Schema(description = "题目提示")
    private String hint;

    @Schema(description = "测试用例列表")
    private List<QuestionCaseRequest> cases;

    @Schema(description = "编程语言列表")
    private List<QuestionLanguageRequest> languages;
}
