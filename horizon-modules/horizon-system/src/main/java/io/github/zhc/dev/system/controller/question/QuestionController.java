package io.github.zhc.dev.system.controller.question;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.model.dto.question.QuestionAddRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionEditRequest;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.model.vo.question.QuestionDetailVO;
import io.github.zhc.dev.system.service.question.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhc.dev
 * @date 2025/3/30 22:41
 */
@RestController
@Tag(name = "题目接口")
@RequestMapping("/question")
public class QuestionController extends BaseController {
    @Resource
    private QuestionService questionService;

    /**
     * 获取题目列表
     *
     * @param questionQueryRequest 题目信息
     * @return R
     */
    @GetMapping("/list")
    public TableData list(@Validated @NotNull QuestionQueryRequest questionQueryRequest) {
        return getTableData(questionService.list(questionQueryRequest));
    }

    /**
     * 添加题目
     *
     * @param questionAddRequest 题目信息
     * @return R
     */
    @PostMapping("/add")
    public R<Void> add(@Validated @NotNull @RequestBody QuestionAddRequest questionAddRequest) {
        return toR(questionService.add(questionAddRequest));
    }

    /**
     * 获取题目详情
     *
     * @param questionId 题目id
     * @return R
     */
    @GetMapping("/detail")
    public R<QuestionDetailVO> detail(Long questionId) {
        return R.ok(questionService.detail(questionId));
    }

    /**
     * 修改题目
     *
     * @param questionEditRequest 题目信息
     * @return R
     */
    @PutMapping("/edit")
    public R<Void> edit(@RequestBody QuestionEditRequest questionEditRequest) {
        return toR(questionService.edit(questionEditRequest));
    }

    /**
     * 删除题目
     *
     * @param questionId 题目id
     * @return R
     */
    @DeleteMapping("/delete")
    public R<Void> delete(Long questionId) {
        return toR(questionService.delete(questionId));
    }
}