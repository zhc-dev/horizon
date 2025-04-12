package io.github.zhc.dev.friend.controller.user;

import io.github.zhc.dev.common.core.constants.HttpConstants;
import io.github.zhc.dev.common.core.controller.BaseController;
import io.github.zhc.dev.common.core.model.entity.R;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.dto.contest.ContestRequest;
import io.github.zhc.dev.friend.service.user.UserContestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhc-dev
 * @data 2025/4/11 20:32
 */
@RestController
@RequestMapping("/user/contest")
public class UserContestController extends BaseController {
    @Resource
    private UserContestService userContestService;

//    @CheckUserStatus
    @PostMapping("/enter")
    public R<Void> enter(@RequestHeader(HttpConstants.AUTHENTICATION) String token, @RequestBody ContestRequest contestRequest) {
        return toR(userContestService.enter(token, contestRequest.getContestId()));
    }

    @GetMapping("/list")
    public TableData list(ContestQueryRequest contestQueryRequest) {
        return userContestService.list(contestQueryRequest);
    }
}
