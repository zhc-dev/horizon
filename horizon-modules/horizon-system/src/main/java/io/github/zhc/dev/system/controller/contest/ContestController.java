package io.github.zhc.dev.system.controller.contest;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.model.dto.contest.ContestAddRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestEditRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQuestionAddRequest;
import io.github.zhc.dev.system.model.vo.contest.ContestDetailVO;
import io.github.zhc.dev.system.service.contest.ContestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhc.dev
 * @date 2025/4/4 21:53
 */
@RestController
@RequestMapping("/contest")
public class ContestController extends BaseController {

    @Resource
    private ContestService contestService;

    /**
     * 查询竞赛列表
     * @param contestQueryRequest 查询请求
     * @return 竞赛列表
     */
    @GetMapping("/list")
    public TableData list(ContestQueryRequest contestQueryRequest) {
        return getTableData(contestService.list(contestQueryRequest));
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody ContestAddRequest contestAddRequest) {
        return R.ok(contestService.add(contestAddRequest));
    }

    @PostMapping("/question/add")
    public R<Void> questionAdd(@RequestBody ContestQuestionAddRequest contestQuestionAddRequest) {
        return toR(contestService.questionAdd(contestQuestionAddRequest));
    }

    @DeleteMapping("/question/delete")
    public R<Void> questionDelete(Long contestId, Long questionId) {
        return toR(contestService.questionDelete(contestId, questionId));
    }

    @GetMapping("/detail")
    public R<ContestDetailVO> detail(Long contestId) {
        return R.ok(contestService.detail(contestId));
    }

    @PutMapping("/edit")
    public R<Void> edit(@RequestBody ContestEditRequest contestEditRequest) {
        return toR(contestService.edit(contestEditRequest));
    }

    @DeleteMapping("/delete")
    public R<Void> delete(Long contestId) {
        return toR(contestService.delete(contestId));
    }

    @PutMapping("/publish")
    public R<Void> publish(Long contestId) {
        return toR(contestService.publish(contestId));
    }

    @PutMapping("/cancelPublish")
    public R<Void> cancelPublish(Long contestId) {
        return toR(contestService.cancelPublish(contestId));
    }

}