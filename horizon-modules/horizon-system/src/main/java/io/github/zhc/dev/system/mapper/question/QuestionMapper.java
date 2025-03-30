package io.github.zhc.dev.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;

/**
 * @author zhc.dev
 * @date 2025/3/30 23:02
 */
public interface QuestionMapper extends BaseMapper<Question> {
    QuestionVO selectQuestionList(QuestionQueryRequest questionQueryRequest);
}
