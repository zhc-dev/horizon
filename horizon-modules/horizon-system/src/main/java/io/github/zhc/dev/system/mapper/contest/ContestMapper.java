package io.github.zhc.dev.system.mapper.contest;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.zhc.dev.system.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.system.model.entity.contest.Contest;
import io.github.zhc.dev.system.model.vo.contest.ContestVO;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/4 22:39
 */
public interface ContestMapper extends BaseMapper<Contest> {
    List<ContestVO> selectContestList(ContestQueryRequest contestQueryRequest);
}
