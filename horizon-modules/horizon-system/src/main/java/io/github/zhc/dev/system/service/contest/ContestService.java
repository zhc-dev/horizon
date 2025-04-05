package io.github.zhc.dev.system.service.contest;

import io.github.zhc.dev.system.model.dto.contest.ContestAddRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestEditRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQuestionAddRequest;
import io.github.zhc.dev.system.model.vo.contest.ContestDetailVO;
import io.github.zhc.dev.system.model.vo.contest.ContestVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/4 22:37
 */
public interface ContestService {
    List<ContestVO> list(ContestQueryRequest contestQueryRequest);

    String add(ContestAddRequest contestAddRequest);

    boolean questionAdd(ContestQuestionAddRequest contestQuestionAddRequest);

    int questionDelete(Long contestId, Long questionId);

    ContestDetailVO detail(Long contestId);

    int edit(ContestEditRequest contestEditRequest);

    int delete(Long contestId);

    int publish(Long contestId);

    int cancelPublish(Long contestId);
}
