package io.github.zhc.dev.job.model.entity.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:51
 */
@Getter
@Setter
public class UserScore {
    private Long contestId;

    private Long userId;

    private int score;

    private int contestRank;
}
