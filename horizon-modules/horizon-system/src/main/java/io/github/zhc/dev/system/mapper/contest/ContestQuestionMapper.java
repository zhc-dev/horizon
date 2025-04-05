package io.github.zhc.dev.system.mapper.contest;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.system.model.entity.contest.ContestQuestion;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/5 10:16
 */
public interface ContestQuestionMapper extends BaseMapper<ContestQuestion> {
    List<QuestionVO> selectContestQuestionList(Long contestId);
}
