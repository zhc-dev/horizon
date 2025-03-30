package io.github.zhc.dev.system.service.question;

import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/3/30 22:58
 */
public interface QuestionService {
    /**
     * 查询题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 题目列表(分页)
     */
    List<QuestionVO> list(QuestionQueryRequest questionQueryRequest);
}
