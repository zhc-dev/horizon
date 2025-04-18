package io.github.zhc.dev.friend.model.vo.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/18 20:55
 */
@Getter
@Setter
public class QuestionLanguageVO {
    @Schema(description = "题目语言id")
    private Long questionLanguageId;

    @Schema(description = "编程语言id")
    private Long languageId;

    @Schema(description = "编程语言")
    private String name;

    @Schema(description = "时间限制")
    private Integer timeLimit;

    @Schema(description = "空间限制")
    private Integer spaceLimit;

    @Schema(description = "默认代码")
    private String defaultCode;

    @Schema(description = "main函数")
    private String mainFunc;
}
