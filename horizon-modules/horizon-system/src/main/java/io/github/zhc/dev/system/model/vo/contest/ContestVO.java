package io.github.zhc.dev.system.model.vo.contest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhc.dev
 * @date 2025/4/4 22:38
 */
@Getter
@Setter
public class ContestVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long contestId;
    private String title;
    private Integer status;
    private String createName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
