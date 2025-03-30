package io.github.zhc.dev.common.core.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询参数
 *
 * @author zhc.dev
 * @date 2025/3/30 23:25
 */
@Getter
@Setter
public class PageQueryRequest {
    @Schema(description = "每页条数")
    private Integer pageSize = 10;

    @Schema(description = "当前页码")
    private Integer pageNum = 1;
}