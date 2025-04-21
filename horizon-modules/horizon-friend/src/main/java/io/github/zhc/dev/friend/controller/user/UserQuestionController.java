package io.github.zhc.dev.friend.controller.user;

import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.friend.model.dto.user.UserSubmitRequest;
import io.github.zhc.dev.friend.model.vo.user.UserQuestionResultVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhc-dev
 * @data 2025/4/20 00:00
 */
@RestController
@RequestMapping("/user/question")
public class UserQuestionController extends BaseController {

    @RequestMapping("/submit")
    public R<UserQuestionResultVO> submit(@RequestBody UserSubmitRequest userSubmitRequest) {
        return null;
    }
}
