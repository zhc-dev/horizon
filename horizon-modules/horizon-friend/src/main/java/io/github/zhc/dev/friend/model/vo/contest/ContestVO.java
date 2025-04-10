package io.github.zhc.dev.friend.model.vo.contest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:39
 */
@Getter
@Setter
public class ContestVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long contestId;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private boolean enter = false; //true: 已经报名  false 未报名
}
