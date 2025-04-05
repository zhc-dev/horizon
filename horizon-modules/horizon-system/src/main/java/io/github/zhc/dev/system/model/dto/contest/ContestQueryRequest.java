package io.github.zhc.dev.system.model.dto.contest;

import io.github.zhc.dev.common.core.model.entity.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc.dev
 * @date 2025/4/4 22:34
 */
@Getter
@Setter
public class ContestQueryRequest extends PageQueryRequest {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;
}
