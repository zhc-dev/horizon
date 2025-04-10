package io.github.zhc.dev.friend.service.impl.contest;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.entity.TableData;
import io.github.zhc.dev.common.core.utils.ThreadLocalUtil;
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
    public TableData listByCache(ContestQueryRequest contestQueryRequest) {
        //从redis当中获取  竞赛列表的数据
        Long total = contestCacheManager.getListSize(contestQueryRequest.getType(), null);
        List<ContestVO> contestVOList;
        if (total == null || total <= 0) {
            contestVOList = list(contestQueryRequest);
            contestCacheManager.refreshCache(contestQueryRequest.getType(), null);
            total = new PageInfo<>(contestVOList).getTotal();
        } else {
            contestVOList = contestCacheManager.getContestVOList(contestQueryRequest, null);
            total = contestCacheManager.getListSize(contestQueryRequest.getType(), null);
        }
        if (CollectionUtil.isEmpty(contestVOList)) {
            return TableData.empty();
        }
        assembleContestVOList(contestVOList);
        return TableData.success(contestVOList, total);
    }

    private void assembleContestVOList(List<ContestVO> contestVOList) {
        Long userId = ThreadLocalUtil.get(Constants.USER_ID, Long.class);
        List<Long> userContestList = contestCacheManager.getAllUserContestList(userId);
        if (CollectionUtil.isEmpty(userContestList)) {
            return;
        }
        for (ContestVO contestVO : contestVOList) {
            if (userContestList.contains(contestVO.getContestId())) {
                contestVO.setEnter(true);
            }
        }
    }
}
