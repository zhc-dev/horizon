package io.github.zhc.dev.friend.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.friend.mapper.contest.ContestQuestionMapper;
import io.github.zhc.dev.friend.mapper.contest.ContestMapper;
import io.github.zhc.dev.friend.mapper.user.UserContestMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;
import io.github.zhc.dev.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/9 20:41
 */
@Component
public class ContestCacheManager {
    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestQuestionMapper examQuestionMapper;

    @Resource
    private UserContestMapper userContestMapper;

    @Resource
    private RedisService redisService;

    public Long getExamQuestionListSize(Long examId) {
        String examQuestionListKey = getExamQuestionListKey(examId);
        return redisService.getListSize(examQuestionListKey);
    }

    public Long getRankListSize(Long examId) {
        return redisService.getListSize(getExamRankListKey(examId));
    }


    private String getDetailKey(Long examId) {
        return CacheConstants.CONTEST_DETAIL_PREFIX + examId;
    }

    private String getUserExamListKey(Long userId) {
        return CacheConstants.USER_EXAM_LIST + userId;
    }

    private String getExamQuestionListKey(Long examId) {
        return CacheConstants.CONTEST_QUESTION_LIST + examId;
    }

    private String getExamRankListKey(Long examId) {
        return CacheConstants.EXAM_RANK_LIST + examId;
    }
}
