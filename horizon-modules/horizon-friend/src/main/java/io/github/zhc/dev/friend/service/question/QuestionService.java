package io.github.zhc.dev.friend.service.question;

import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.friend.model.vo.question.QuestionDetailVO;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:14
 */
public interface QuestionService {
    TableData list(QuestionQueryRequest questionQueryRequest);

    QuestionDetailVO detail(Long questionId);
}
