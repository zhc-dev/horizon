package io.github.zhc.dev.friend.model.vo.question;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/18 20:55
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
