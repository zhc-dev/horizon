package io.github.zhc.dev.friend.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.elasticsearch.QuestionRepository;
import io.github.zhc.dev.friend.mapper.question.QuestionMapper;
import io.github.zhc.dev.friend.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.friend.model.entity.question.Question;
import io.github.zhc.dev.friend.model.entity.question.es.QuestionES;
import io.github.zhc.dev.friend.model.vo.question.QuestionVO;
import io.github.zhc.dev.friend.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:17
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionRepository questionRepository;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public TableData list(QuestionQueryRequest questionQueryRequest) {
        long count = questionRepository.count();
        if (count <= 0) {
            refreshQuestions();
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(questionQueryRequest.getPageNum() - 1, questionQueryRequest.getPageSize(), sort);
        Integer difficulty = questionQueryRequest.getDifficulty();
        String keyword = questionQueryRequest.getKeyword();

        Page<QuestionES> questionESPage;
        if (difficulty == null && StrUtil.isEmpty(keyword)) {
            questionESPage = questionRepository.findAll(pageable);
        } else if (StrUtil.isEmpty(keyword)) {
            questionESPage = questionRepository.findQuestionByDifficulty(difficulty, pageable);
        } else if (difficulty == null) {
            questionESPage = questionRepository.findByTitleOrContentOrTagsOrSourceOrHint(keyword, keyword, keyword, keyword, keyword, pageable);
        } else {
            questionESPage = questionRepository.findByTitleOrContentOrTagsOrSourceOrHintAndDifficulty(keyword, keyword, keyword, keyword, keyword, difficulty, pageable);
        }
        long total = questionESPage.getTotalElements();
        if (total <= 0) {
            return TableData.empty();
        }
        List<QuestionES> questionESList = questionESPage.getContent();
        List<QuestionVO> questionVOList = BeanUtil.copyToList(questionESList, QuestionVO.class);
        return TableData.success(questionVOList, total);
    }

    @Override
    public TableData refreshQuestions() {
        // 清空现有索引
        questionRepository.deleteAll();
        
        // 重新导入数据
        List<Question> questionList = questionMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtil.isEmpty(questionList)) {
            return TableData.empty();
        }
        List<QuestionES> questionESList = BeanUtil.copyToList(questionList, QuestionES.class);
        questionRepository.saveAll(questionESList);
        
        return TableData.success("刷新成功", questionList.size());
    }
}
