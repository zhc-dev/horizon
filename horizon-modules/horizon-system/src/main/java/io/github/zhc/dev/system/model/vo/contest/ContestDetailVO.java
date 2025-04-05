package io.github.zhc.dev.system.model.vo.contest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/5 10:02
 */
@Getter
@Setter
public class ContestDetailVO {
    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private List<QuestionVO> examQuestionList;
}
