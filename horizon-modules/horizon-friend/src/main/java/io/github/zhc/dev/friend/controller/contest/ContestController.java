package io.github.zhc.dev.friend.controller.contest;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.dto.contest.ContestRankRequest;
import io.github.zhc.dev.friend.service.contest.ContestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:34
 */
@RestController
@RequestMapping("/contest")
public class ContestController extends BaseController {
    @Resource
    private ContestService contestService;

    /**
     * 查询竞赛列表
     *
     * @param contestQueryRequest 查询请求
     * @return 竞赛列表
     */
    @GetMapping("/semi/login/list")
    public TableData list(ContestQueryRequest contestQueryRequest) {
        return getTableData(contestService.list(contestQueryRequest));
    }

    /**
     * 查询竞赛列表
     *
     * @param contestQueryRequest 查询请求
     * @return 竞赛列表
     */
    @GetMapping("/semi/login/cache/list")
    public TableData listByCache(ContestQueryRequest contestQueryRequest) {
        return contestService.listByCache(contestQueryRequest);
    }

    @GetMapping("/rank/list")
    public TableData rankList(ContestRankRequest contestRankRequest) {
        return contestService.rankList(contestRankRequest);
    }

    @GetMapping("/first")
    public R<String> getFirstQuestion(Long contestId) {
        return R.ok(contestService.getFirstQuestion(contestId));
    }

    @GetMapping("/pre")
    public R<String> preQuestion(Long contestId, Long questionId) {
        return R.ok(contestService.preQuestion(contestId, questionId));
    }

    @GetMapping("/next")
    public R<String> nextQuestion(Long contestId, Long questionId) {
        return R.ok(contestService.nextQuestion(contestId, questionId));
    }

}
