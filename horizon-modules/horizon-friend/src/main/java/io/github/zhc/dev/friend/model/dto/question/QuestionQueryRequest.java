package io.github.zhc.dev.friend.model.dto.question;

import io.github.zhc.dev.common.core.model.entity.PageQueryRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:15
 */
@Getter
@Setter
public class QuestionQueryRequest extends PageQueryRequest {

    private String keyword;

    private Integer difficulty;
}
