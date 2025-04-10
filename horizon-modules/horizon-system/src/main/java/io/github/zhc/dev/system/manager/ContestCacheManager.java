package io.github.zhc.dev.system.manager;

import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.redis.service.RedisService;
import io.github.zhc.dev.system.model.entity.contest.Contest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author zhc.dev
 * @date 2025/4/5 11:34
 */
@Component
public class ContestCacheManager {

    @Resource
    private RedisService redisService;

    public void addCache(Contest contest) {
        redisService.leftPushForList(getContestListKey(), contest.getContestId());
        redisService.setCacheObject(getDetailKey(contest.getContestId()), contest);
    }

    public void deleteCache(Long contestId) {
        redisService.removeForList(getContestListKey(), contestId);
        redisService.deleteObject(getDetailKey(contestId));
        redisService.deleteObject(getContestQuestionListKey(contestId));
    }

    private String getContestListKey() {
        return CacheConstants.CONTEST_UNFINISHED_LIST_KEY;
    }

    private String getDetailKey(Long contestId) {
        return CacheConstants.CONTEST_DETAIL_KEY_PREFIX + contestId;
    }

    private String getContestQuestionListKey(Long contestId) {
        return CacheConstants.CONTEST_QUESTION_LIST_KEY_PREFIX + contestId;
    }
}
