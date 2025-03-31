package io.github.zhc.dev.system.service.impl.question;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.mapper.question.QuestionMapper;
import io.github.zhc.dev.system.model.dto.question.QuestionAddRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.entity.question.Question;
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
}
