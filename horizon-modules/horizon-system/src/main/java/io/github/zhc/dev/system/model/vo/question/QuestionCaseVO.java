package io.github.zhc.dev.system.model.vo.question;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/4 14:39
 */
@Getter
@Setter
public class QuestionCaseVO {
    private Long caseId;
    private String input;
    private String output;
    private Integer isSample;
    private Integer score;
}
