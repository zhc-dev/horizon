package io.github.zhc.dev.system.controller.question;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.system.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.system.service.question.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public TableData list(@Validated @RequestBody @NotNull QuestionQueryRequest questionQueryRequest) {
        return getTableData(questionService.list(questionQueryRequest));
    }
}