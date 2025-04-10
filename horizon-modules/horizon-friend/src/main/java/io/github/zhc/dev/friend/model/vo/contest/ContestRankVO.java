package io.github.zhc.dev.friend.model.vo.contest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhc-dev
 * @data 2025/4/10 20:56
 */
@Getter
@Setter
public class ContestRankVO {
    private Long userId;

    private String nickName;

    private int contestRank;

    private int score;
}
