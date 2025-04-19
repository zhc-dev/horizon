package io.github.zhc.dev.friend.controller.question;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.question.QuestionQueryRequest;
import io.github.zhc.dev.friend.model.vo.question.QuestionDetailVO;
import io.github.zhc.dev.friend.model.vo.question.QuestionVO;
import io.github.zhc.dev.friend.service.question.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/detail")
    public R<QuestionDetailVO> detail(Long questionId) {
        return R.ok(questionService.detail(questionId));
    }

    @GetMapping("/semi/login/hot-list")
    public R<List<QuestionVO>> hotList() {
        return R.ok(questionService.hotList());
    }

    @GetMapping("/pre")
    public R<String> preQuestion(Long questionId) {
        return R.ok(questionService.preQuestion(questionId));
    }

    @GetMapping("/next")
    public R<String> nextQuestion(Long questionId) {
        return R.ok(questionService.nextQuestion(questionId));
    }
}
