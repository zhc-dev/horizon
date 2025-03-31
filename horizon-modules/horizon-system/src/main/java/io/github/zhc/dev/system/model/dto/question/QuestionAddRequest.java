package io.github.zhc.dev.system.model.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/31 22:32
 */
@Getter
@Setter
public class QuestionAddRequest {
    @Schema(description = "题目标题")
    private String title;

    @Schema(description = "题目难度")
    private Integer difficulty;

    @Schema(description = "时间限制")
    private Long timeLimit;

    @Schema(description = "空间限制")
    private Long spaceLimit;

    @Schema(description = "题目内容")
    private String content;

    @Schema(description = "测试用例")
    private String questionCase;

    @Schema(description = "默认代码块")
    private String defaultCode;

    @Schema(description = "main函数")
    private String mainFunc;
}
