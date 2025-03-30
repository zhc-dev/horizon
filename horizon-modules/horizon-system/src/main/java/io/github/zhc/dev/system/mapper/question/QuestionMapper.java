package io.github.zhc.dev.system.mapper.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/3/30 23:02
 */
public interface QuestionMapper extends BaseMapper<Question> {
    List<QuestionVO> selectQuestionList(QuestionQueryRequest questionQueryRequest);
}
