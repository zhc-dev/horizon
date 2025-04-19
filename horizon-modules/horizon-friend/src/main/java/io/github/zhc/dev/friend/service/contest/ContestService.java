package io.github.zhc.dev.friend.service.contest;

import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.dto.contest.ContestRankRequest;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:38
 */
public interface ContestService {
    List<ContestVO> list(ContestQueryRequest contestQueryRequest);

    TableData listByCache(ContestQueryRequest contestQueryRequest);

    TableData rankList(ContestRankRequest contestRankRequest);

    String getFirstQuestion(Long contestId);

    String preQuestion(Long contestId, Long questionId);

    String nextQuestion(Long contestId, Long questionId);
}
