package io.github.zhc.dev.friend.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.elasticsearch.QuestionRepository;
import io.github.zhc.dev.friend.manager.QuestionCacheManager;
import io.github.zhc.dev.friend.mapper.question.QuestionCaseMapper;
import io.github.zhc.dev.friend.mapper.question.QuestionLanguageMapper;
import io.github.zhc.dev.friend.mapper.question.QuestionMapper;
import io.github.zhc.dev.friend.mapper.user.UserSubmitMapper;
import io.github.zhc.dev.friend.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.friend.model.entity.question.Question;
import io.github.zhc.dev.friend.model.entity.question.QuestionCase;
import io.github.zhc.dev.friend.model.entity.question.QuestionLanguage;
import io.github.zhc.dev.friend.model.entity.question.es.QuestionCaseES;
import io.github.zhc.dev.friend.model.entity.question.es.QuestionES;
import io.github.zhc.dev.friend.model.entity.question.es.QuestionLanguageES;
import io.github.zhc.dev.friend.model.vo.question.QuestionCaseVO;
import io.github.zhc.dev.friend.model.vo.question.QuestionDetailVO;
import io.github.zhc.dev.friend.model.vo.question.QuestionLanguageVO;
import io.github.zhc.dev.friend.model.vo.question.QuestionVO;
import io.github.zhc.dev.friend.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Resource
    private QuestionCaseMapper questionCaseMapper;

    @Resource
    private QuestionLanguageMapper questionLanguageMapper;

    @Resource
    private QuestionCacheManager questionCacheManager;

    @Resource
    private UserSubmitMapper userSubmitMapper;

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
            questionESPage = questionRepository.findByTitleOrContentOrTagsOrSourceOrHint(keyword, keyword, keyword, pageable);
        } else {
            questionESPage = questionRepository.findByTitleOrContentOrTagsOrSourceOrHintAndDifficulty(keyword, keyword, keyword, difficulty, pageable);
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
    public QuestionDetailVO detail(Long questionId) {
        QuestionES questionES = questionRepository.findById(questionId).orElse(null);
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        if (questionES != null) {
            BeanUtil.copyProperties(questionES, questionDetailVO);
            questionDetailVO.getCases().clear();
            for (QuestionCaseES questionCaseES : questionES.getCases()) {
                if (questionCaseES.getIsSample() == 0) continue;// 不是样例无需展示,不能返回给前端
                QuestionCaseVO questionCaseVO = new QuestionCaseVO();
                BeanUtil.copyProperties(questionCaseES, questionCaseVO);
                questionDetailVO.getCases().add(questionCaseVO);
            }
            questionDetailVO.setLanguages(BeanUtil.copyToList(questionES.getLanguages(), QuestionLanguageVO.class));
            return questionDetailVO;
        }
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            return null;
        }
        refreshQuestions();
        BeanUtil.copyProperties(question, questionDetailVO);
        return questionDetailVO;
    }

    @Override
    public String preQuestion(Long questionId) {
        Long listSize = questionCacheManager.getListSize();
        if (listSize == null || listSize <= 0) {
            questionCacheManager.refreshCache();
        }
        return questionCacheManager.preQuestion(questionId).toString();
    }

    @Override
    public String nextQuestion(Long questionId) {
        Long listSize = questionCacheManager.getListSize();
        if (listSize == null || listSize <= 0) {
            questionCacheManager.refreshCache();
        }
        return questionCacheManager.nextQuestion(questionId).toString();
    }

    @Override
    public List<QuestionVO> hotList() {
        Long total = questionCacheManager.getHostListSize();
        List<Long> hotQuestionIdList;
        if (total == null || total <= 0) {
            PageHelper.startPage(Constants.HOST_QUESTION_LIST_START, Constants.HOST_QUESTION_LIST_END);
            hotQuestionIdList = userSubmitMapper.selectHostQuestionList();
            questionCacheManager.refreshHotQuestionList(hotQuestionIdList);
        } else {
            hotQuestionIdList = questionCacheManager.getHostList();
        }
        return assembleQuestionVOList(hotQuestionIdList);
    }

    private void refreshQuestions() {
        List<Question> questionList = questionMapper.selectList(new LambdaQueryWrapper<Question>().eq(Question::getIsDeleted, 0));
        if (CollectionUtil.isEmpty(questionList)) {
            return;
        }
        List<QuestionES> questionESList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionES questionES = new QuestionES();
            BeanUtil.copyProperties(question, questionES);
            questionES.setCases(BeanUtil.copyToList(questionCaseMapper.selectList(new LambdaQueryWrapper<QuestionCase>().eq(QuestionCase::getQuestionId, question.getQuestionId())), QuestionCaseES.class));
            questionES.setLanguages(BeanUtil.copyToList(questionLanguageMapper.selectList(new LambdaQueryWrapper<QuestionLanguage>().eq(QuestionLanguage::getQuestionId, question.getQuestionId())), QuestionLanguageES.class));
            questionESList.add(questionES);
        }
        questionRepository.saveAll(questionESList);
    }

    private List<QuestionVO> assembleQuestionVOList(List<Long> hotQuestionIdList) {
        if (CollectionUtil.isEmpty(hotQuestionIdList)) {
            return new ArrayList<>();
        }
        List<QuestionVO> resultList = new ArrayList<>();
        for (Long questionId : hotQuestionIdList) {
            QuestionVO questionVO = new QuestionVO();
            QuestionDetailVO detail = detail(questionId);
            questionVO.setTitle(detail.getTitle());
            resultList.add(questionVO);
        }
        return resultList;
    }
}
