package io.github.zhc.dev.system.model.dto.question;

import io.github.zhc.dev.common.core.model.entity.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author zhc.dev
 * @date 2025/3/30 22:44
 */
@Getter
@Setter
public class QuestionQueryRequest extends PageQueryRequest {
    @Schema(description = "题目标题")
    private String title;
    @Schema(description = "题目难度")
    private Integer difficulty;
    // mybatis xml 查询使用
    private String excludeIdStr;
    // mybatis xml 查询使用
    private Set<Long> excludeIdSet;
}
