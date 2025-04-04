package io.github.zhc.dev.system.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.system.mapper.language.LanguageMapper;
import io.github.zhc.dev.system.mapper.question.QuestionCaseMapper;
import io.github.zhc.dev.system.mapper.question.QuestionLanguageMapper;
import io.github.zhc.dev.system.mapper.question.QuestionMapper;
import io.github.zhc.dev.system.model.dto.question.QuestionAddRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionEditRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.entity.question.QuestionCase;
import io.github.zhc.dev.system.model.entity.question.QuestionLanguage;
import io.github.zhc.dev.system.model.vo.question.QuestionCaseVO;
import io.github.zhc.dev.system.model.vo.question.QuestionDetailVO;
import io.github.zhc.dev.system.model.vo.question.QuestionLanguageVO;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;
import io.github.zhc.dev.system.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/3/30 23:00
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionCaseMapper questionCaseMapper;

    @Resource
    QuestionLanguageMapper questionLanguageMapper;

    @Resource
    private LanguageMapper languageMapper;

    /**
     * 查询题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 题目列表
     */
    @Override
    public List<QuestionVO> list(QuestionQueryRequest questionQueryRequest) {
        // 开始分页查询,查询前会先执行count, 所以不需要再查询总数了
        PageHelper.startPage(questionQueryRequest.getPageNum(), questionQueryRequest.getPageSize());
        return questionMapper.selectQuestionList(questionQueryRequest);
    }

    /**
     * 添加题目
     *
     * @param questionAddRequest 添加请求
     * @return 添加结果(受影响的行数)
     */
    @Override
    public int add(QuestionAddRequest questionAddRequest) {
        Question question = new Question();
        BeanUtil.copyProperties(questionAddRequest, question);
        return questionMapper.insert(question);
    }

    /**
     * 查询题目详情
     *
     * @param questionId 题目id
     * @return 题目详情
     */
    @Override
    public QuestionDetailVO detail(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        List<QuestionCase> cases = questionCaseMapper.selectList(new LambdaQueryWrapper<QuestionCase>().eq(QuestionCase::getQuestionId, questionId));
        List<QuestionLanguage> languages = questionLanguageMapper.selectList(new LambdaQueryWrapper<QuestionLanguage>().eq(QuestionLanguage::getQuestionId, questionId));
        if (question == null || cases == null || languages == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        BeanUtil.copyProperties(question, questionDetailVO);
        List<QuestionCaseVO> casesVO = BeanUtil.copyToList(cases, QuestionCaseVO.class);
        List<QuestionLanguageVO> languageVOS = BeanUtil.copyToList(languages, QuestionLanguageVO.class);
        for (int i = 0; i < languages.size(); i++) {
            languageVOS.get(i).setName(languageMapper.selectById(languages.get(i)).getName());
        }
        questionDetailVO.setCases(casesVO);
        questionDetailVO.setLanguages(languageVOS);
        return questionDetailVO;
    }

    /**
     * 修改题目
     *
     * @param questionEditRequest 修改请求
     * @return 修改结果(受影响的行数)
     */
    @Override
    public int edit(QuestionEditRequest questionEditRequest) {
        Question oldQuestion = questionMapper.selectById(questionEditRequest.getQuestionId());
        if (oldQuestion == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);

        // 封装题目
        oldQuestion.setTitle(questionEditRequest.getTitle());
        oldQuestion.setDifficulty(questionEditRequest.getDifficulty());
//        oldQuestion.setTimeLimit(questionEditRequest.getTimeLimit());
//        oldQuestion.setSpaceLimit(questionEditRequest.getSpaceLimit());
        oldQuestion.setContent(questionEditRequest.getContent());
//        oldQuestion.setQuestionCase(questionEditRequest.getQuestionCase());
//        oldQuestion.setDefaultCode(questionEditRequest.getDefaultCode());
//        oldQuestion.setMainFunc(questionEditRequest.getMainFunc());
        return questionMapper.updateById(oldQuestion);
    }

    @Override
    public int delete(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if (question == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        return questionMapper.deleteById(questionId);
    }

}
