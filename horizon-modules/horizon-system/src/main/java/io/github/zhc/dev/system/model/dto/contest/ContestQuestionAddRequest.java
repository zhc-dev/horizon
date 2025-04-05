package io.github.zhc.dev.system.model.dto.contest;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

/**
 * @author zhc.dev
 * @date 2025/4/5 10:00
 */
@Getter
@Setter
public class ContestQuestionAddRequest {
    private Long examId;
    private LinkedHashSet<Long> questionIdSet;
}
