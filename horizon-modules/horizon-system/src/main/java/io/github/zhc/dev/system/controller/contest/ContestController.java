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
     *
     * @param contestQueryRequest 查询请求
     * @return 竞赛列表
     */
    @GetMapping("/list")
    public TableData list(ContestQueryRequest contestQueryRequest) {
        return getTableData(contestService.list(contestQueryRequest));
    }

    /**
     * 添加竞赛
     *
     * @param contestAddRequest 竞赛添加请求
     * @return 竞赛id
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody ContestAddRequest contestAddRequest) {
        return R.ok(contestService.add(contestAddRequest));
    }

    /**
     * 添加竞赛题目
     *
     * @param contestQuestionAddRequest 竞赛题目添加请求
     * @return 是否添加成功
     */
    @PostMapping("/question/add")
    public R<Void> questionAdd(@RequestBody ContestQuestionAddRequest contestQuestionAddRequest) {
        return toR(contestService.questionAdd(contestQuestionAddRequest));
    }

    /**
     * 删除竞赛题目
     *
     * @param contestId  竞赛id
     * @param questionId 竞赛题目id
     * @return 是否删除成功
     */
    @DeleteMapping("/question/delete")
    public R<Void> questionDelete(Long contestId, Long questionId) {
        return toR(contestService.questionDelete(contestId, questionId));
    }

    /**
     * 获取竞赛详情
     *
     * @param contestId 竞赛id
     * @return 竞赛详情
     */
    @GetMapping("/detail")
    public R<ContestDetailVO> detail(Long contestId) {
        return R.ok(contestService.detail(contestId));
    }

    /**
     * 编辑竞赛
     *
     * @param contestEditRequest 竞赛编辑请求
     * @return 是否编辑成功
     */
    @PutMapping("/edit")
    public R<Void> edit(@RequestBody ContestEditRequest contestEditRequest) {
        return toR(contestService.edit(contestEditRequest));
    }

    /**
     * 删除竞赛
     *
     * @param contestId 竞赛id
     * @return 是否删除成功
     */
    @DeleteMapping("/delete")
    public R<Void> delete(Long contestId) {
        return toR(contestService.delete(contestId));
    }

    /**
     * 发布竞赛
     *
     * @param contestId 竞赛id
     * @return 是否发布成功
     */
    @PutMapping("/publish")
    public R<Void> publish(Long contestId) {
        return toR(contestService.publish(contestId));
    }

    /**
     * 取消发布竞赛
     *
     * @param contestId 竞赛id
     * @return 是否取消发布成功
     */
    @PutMapping("/cancelPublish")
    public R<Void> cancelPublish(Long contestId) {
        return toR(contestService.cancelPublish(contestId));
    }
}