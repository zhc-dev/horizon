package io.github.zhc.dev.system.model.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/3/31 23:22
 */
@Getter
@Setter
public class QuestionEditRequest extends QuestionAddRequest {
    @Schema(description = "题目id")
    private Long questionId;
}
