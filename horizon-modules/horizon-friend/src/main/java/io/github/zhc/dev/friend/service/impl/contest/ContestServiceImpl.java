package io.github.zhc.dev.friend.service.impl.contest;

import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.friend.manager.ContestCacheManager;
import io.github.zhc.dev.friend.mapper.contest.ContestMapper;
import io.github.zhc.dev.friend.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.friend.model.vo.contest.ContestVO;
import io.github.zhc.dev.friend.service.contest.ContestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc.dev
 * @date 2025/4/8 22:40
 */
@Service
public class ContestServiceImpl implements ContestService {
    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestCacheManager contestCacheManager;

    @Override
    public List<ContestVO> list(ContestQueryRequest contestQueryRequest) {
        PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
        return contestMapper.selectContestList(contestQueryRequest);
    }

    @Override
    public List<?> listByCache(ContestQueryRequest contestQueryRequest) {
        //从redis当中获取  竞赛列表的数据
        return null;
    }
}
