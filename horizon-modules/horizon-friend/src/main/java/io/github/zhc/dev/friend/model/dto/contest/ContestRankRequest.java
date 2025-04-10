package io.github.zhc.dev.friend.model.dto.contest;

import io.github.zhc.dev.common.core.model.entity.PageQueryRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/10 20:58
 */
@Getter
@Setter
public class ContestRankRequest extends PageQueryRequest {
    private Long contestId;
}
