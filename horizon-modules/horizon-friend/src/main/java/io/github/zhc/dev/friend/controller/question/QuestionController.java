package io.github.zhc.dev.friend.controller.question;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.friend.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhc-dev
 * @data 2025/4/14 21:13
 */
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {
    @Resource
    private QuestionService questionService;

    @GetMapping("/semi/login/list")
    public TableData list(QuestionQueryRequest questionQueryRequest) {
        return questionService.list(questionQueryRequest);
    }

}
