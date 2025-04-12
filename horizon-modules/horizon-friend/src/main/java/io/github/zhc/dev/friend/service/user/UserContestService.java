package io.github.zhc.dev.friend.service.user;

import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:34
 */
public interface UserContestService {
    int enter(String token, Long contestId);

    TableData list(ContestQueryRequest contestQueryRequest);
}
