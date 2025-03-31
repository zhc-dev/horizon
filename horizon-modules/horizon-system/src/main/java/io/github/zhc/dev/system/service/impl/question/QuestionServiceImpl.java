package io.github.zhc.dev.system.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.system.mapper.question.QuestionMapper;
import io.github.zhc.dev.system.model.dto.question.QuestionAddRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionEditRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.vo.question.QuestionDetailVO;
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
        if (question == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        BeanUtil.copyProperties(question, questionDetailVO);
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
        oldQuestion.setTimeLimit(questionEditRequest.getTimeLimit());
        oldQuestion.setSpaceLimit(questionEditRequest.getSpaceLimit());
        oldQuestion.setContent(questionEditRequest.getContent());
        oldQuestion.setQuestionCase(questionEditRequest.getQuestionCase());
        oldQuestion.setDefaultCode(questionEditRequest.getDefaultCode());
        oldQuestion.setMainFunc(questionEditRequest.getMainFunc());

        return questionMapper.updateById(oldQuestion);
    }

    @Override
    public int delete(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if (question == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        return questionMapper.deleteById(questionId);
    }

}
