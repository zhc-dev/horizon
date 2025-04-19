package io.github.zhc.dev.friend.manager;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.friend.mapper.question.QuestionMapper;
import io.github.zhc.dev.friend.model.entity.question.Question;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.security.exception.ServiceException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/19 17:32
 */

@Component
public class QuestionCacheManager {

    @Resource
    private RedisService redisService;

    @Resource
    private QuestionMapper questionMapper;

    public Long getListSize() {
        return redisService.getListSize(CacheConstants.QUESTION_LIST_KEY);
    }

    public Long getHostListSize() {
        return redisService.getListSize(CacheConstants.QUESTION_HOST_LIST_KEY);
    }

    public void refreshCache() {
        List<Question> questionList = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                .select(Question::getQuestionId).orderByDesc(Question::getCreateTime));
        if (CollectionUtil.isEmpty(questionList)) {
            return;
        }
        List<Long> questionIdList = questionList.stream().map(Question::getQuestionId).toList();
        redisService.rightPushAll(CacheConstants.QUESTION_LIST_KEY, questionIdList);
    }

    public Long preQuestion(Long questionId) {
        Long index = redisService.indexOfForList(CacheConstants.QUESTION_LIST_KEY, questionId);
        if (index == 0) {
            throw new ServiceException(ResultCode.FAILED_FIRST_QUESTION);
        }
        return redisService.indexForList(CacheConstants.QUESTION_LIST_KEY, index - 1, Long.class);
    }

    public Object nextQuestion(Long questionId) {
        Long index = redisService.indexOfForList(CacheConstants.QUESTION_LIST_KEY, questionId);
        long lastIndex = getListSize() - 1;
        if (index == lastIndex) {
            throw new ServiceException(ResultCode.FAILED_LAST_QUESTION);
        }
        return redisService.indexForList(CacheConstants.QUESTION_LIST_KEY, index + 1, Long.class);
    }

    public List<Long> getHostList() {
        return redisService.getCacheListByRange(CacheConstants.QUESTION_HOST_LIST_KEY,
                CacheConstants.DEFAULT_START, CacheConstants.DEFAULT_END, Long.class);
    }

    public void refreshHotQuestionList(List<Long> hotQuestionIdList) {
        if (CollectionUtil.isEmpty(hotQuestionIdList)) {
            return;
        }
        redisService.rightPushAll(CacheConstants.QUESTION_HOST_LIST_KEY, hotQuestionIdList);
    }
}