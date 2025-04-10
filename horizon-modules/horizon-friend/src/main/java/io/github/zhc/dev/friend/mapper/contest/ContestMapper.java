package io.github.zhc.dev.friend.mapper.contest;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.entity.contest.Contest;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:42
 */
public interface ContestMapper extends BaseMapper<Contest> {
    List<ContestVO> selectContestList(ContestQueryRequest contestQueryRequest);
}
