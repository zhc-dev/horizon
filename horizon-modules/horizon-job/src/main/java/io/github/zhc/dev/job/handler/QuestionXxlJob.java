package io.github.zhc.dev.job.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.job.mapper.user.UserSubmitMapper;
import io.github.zhc.dev.redis.service.RedisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 23:06
 */

@Slf4j
@Component
public class QuestionXxlJob {
    @Resource
    private UserSubmitMapper userSubmitMapper;

    @Resource
    private RedisService redisService;

    @XxlJob("hostQuestionListHandler")
    public void hostQuestionListHandler() {
        log.info("----- 题目热门列表统计开始 ------");
        PageHelper.startPage(Constants.HOST_QUESTION_LIST_START, Constants.HOST_QUESTION_LIST_END);
        List<Long> questionIdList = userSubmitMapper.selectHostQuestionList();
        if (CollectionUtil.isEmpty(questionIdList)) {
            return;
        }
        redisService.deleteObject(CacheConstants.QUESTION_HOST_LIST_KEY);
        redisService.rightPushAll(CacheConstants.QUESTION_HOST_LIST_KEY, questionIdList);
        log.info("----- 题目热门列表统计结束 ------");
    }
}
