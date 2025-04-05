package io.github.zhc.dev.system.model.dto.contest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/5 10:00
 */
@Getter
@Setter
public class ContestEditRequest extends ContestAddRequest {
    private Long contestId;
}