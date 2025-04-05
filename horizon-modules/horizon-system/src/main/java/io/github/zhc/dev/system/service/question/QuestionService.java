package io.github.zhc.dev.system.service.question;

import io.github.zhc.dev.system.model.dto.question.QuestionAddRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionEditRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.vo.question.QuestionDetailVO;
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

    /**
     * 添加题目
     *
     * @param questionAddRequest 添加请求
     * @return 添加结果(受影响的行数)
     */
    int add(QuestionAddRequest questionAddRequest);

    /**
     * 查询题目详情
     *
     * @param questionId 题目id
     * @return 题目详情
     */
    QuestionDetailVO detail(Long questionId);

    /**
     * 修改题目
     *
     * @param questionEditRequest 修改请求
     * @return 修改结果(受影响的行数)
     */
    int edit(QuestionEditRequest questionEditRequest);

    /**
     * 删除题目
     *
     * @param questionId 题目id
     * @return 删除结果(受影响的行数)
     */
    int delete(Long questionId);
}
