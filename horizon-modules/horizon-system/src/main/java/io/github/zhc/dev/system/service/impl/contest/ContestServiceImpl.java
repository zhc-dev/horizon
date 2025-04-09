package io.github.zhc.dev.system.service.impl.contest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.common.core.model.enums.ResultCode;
import io.github.zhc.dev.security.exception.ServiceException;
import io.github.zhc.dev.system.manager.ContestCacheManager;
import io.github.zhc.dev.system.mapper.contest.ContestMapper;
import io.github.zhc.dev.system.mapper.contest.ContestQuestionMapper;
import io.github.zhc.dev.system.mapper.question.QuestionMapper;
import io.github.zhc.dev.system.model.dto.contest.ContestAddRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestEditRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQueryRequest;
import io.github.zhc.dev.system.model.dto.contest.ContestQuestionAddRequest;
import io.github.zhc.dev.system.model.entity.contest.Contest;
import io.github.zhc.dev.system.model.entity.contest.ContestQuestion;
import io.github.zhc.dev.system.model.entity.question.Question;
import io.github.zhc.dev.system.model.vo.contest.ContestDetailVO;
import io.github.zhc.dev.system.model.vo.contest.ContestVO;
import io.github.zhc.dev.system.model.vo.question.QuestionVO;
import io.github.zhc.dev.system.service.contest.ContestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.baomidou.mybatisplus.extension.toolkit.Db.saveBatch;

/**
 * @author zhc.dev
 * @date 2025/4/4 22:39
 */
@Service
public class ContestServiceImpl implements ContestService {

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private ContestQuestionMapper contestQuestionMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private ContestCacheManager contestCacheManager;

    @Override
    public List<ContestVO> list(ContestQueryRequest contestQueryRequest) {
        PageHelper.startPage(contestQueryRequest.getPageNum(), contestQueryRequest.getPageSize());
        return contestMapper.selectContestList(contestQueryRequest);
    }

    @Override
    public String add(ContestAddRequest contestAddRequest) {
        checkContestSaveParams(contestAddRequest, null);
        Contest contest = new Contest();
        BeanUtil.copyProperties(contestAddRequest, contest);
        contestMapper.insert(contest);
        return contest.getContestId().toString();
    }

    @Override
    public boolean questionAdd(ContestQuestionAddRequest contestQuestionAddRequest) {
        Contest contest = getContest(contestQuestionAddRequest.getContestId());
        checkContest(contest);
        if (Constants.TRUE.equals(contest.getStatus())) throw new ServiceException(ResultCode.CONTEST_IS_PUBLISH);
        Set<Long> questionIdSet = contestQuestionAddRequest.getQuestionIdSet();
        if (CollectionUtil.isEmpty(questionIdSet)) return true;

        List<Question> questionList = questionMapper.selectBatchIds(questionIdSet);
        if (CollectionUtil.isEmpty(questionList) || questionList.size() < questionIdSet.size()) {
            throw new ServiceException(ResultCode.CONTEST_QUESTION_NOT_EXISTS);
        }
        return saveContestQuestion(contest, questionIdSet);
    }

    @Override
    public int questionDelete(Long contestId, Long questionId) {
        Contest contest = getContest(contestId);
        checkContest(contest);
        if (Constants.TRUE.equals(contest.getStatus())) throw new ServiceException(ResultCode.CONTEST_IS_PUBLISH);
        return contestQuestionMapper.delete(new LambdaQueryWrapper<ContestQuestion>()
                .eq(ContestQuestion::getContestId, contestId)
                .eq(ContestQuestion::getQuestionId, questionId));
    }

    @Override
    public ContestDetailVO detail(Long contestId) {
        ContestDetailVO contestDetailVO = new ContestDetailVO();
        Contest contest = getContest(contestId);
        BeanUtil.copyProperties(contest, contestDetailVO);
        List<QuestionVO> questionVOList = contestQuestionMapper.selectContestQuestionList(contestId);
        if (CollectionUtil.isEmpty(questionVOList)) {
            return contestDetailVO;
        }
        contestDetailVO.setContestQuestionList(questionVOList);
        return contestDetailVO;
    }

    @Override
    public int edit(ContestEditRequest contestEditRequest) {
        Contest contest = getContest(contestEditRequest.getContestId());
        if (Constants.TRUE.equals(contest.getStatus())) throw new ServiceException(ResultCode.CONTEST_IS_PUBLISH);
        checkContest(contest);
        checkContestSaveParams(contestEditRequest, contestEditRequest.getContestId());
        contest.setTitle(contestEditRequest.getTitle());
        contest.setStartTime(contestEditRequest.getStartTime());
        contest.setEndTime(contestEditRequest.getEndTime());
        contest.setDescription(contestEditRequest.getDescription());
        contest.setAllowedLanguages(contestEditRequest.getAllowedLanguages());
        return contestMapper.updateById(contest);
    }

    @Override
    public int delete(Long contestId) {
        Contest contest = getContest(contestId);
        if (Constants.TRUE.equals(contest.getStatus())) {
            throw new ServiceException(ResultCode.CONTEST_IS_PUBLISH);
        }
        checkContest(contest);
        contestQuestionMapper.delete(new LambdaQueryWrapper<ContestQuestion>()
                .eq(ContestQuestion::getContestId, contestId));
        return contestMapper.deleteById(contest);
    }

    @Override
    public int publish(Long contestId) {
        Contest contest = getContest(contestId);
        if (contest.getEndTime().isBefore(LocalDateTime.now())) throw new ServiceException(ResultCode.CONTEST_IS_FINISH);

        Long count = contestQuestionMapper.selectCount(new LambdaQueryWrapper<ContestQuestion>().eq(ContestQuestion::getContestId, contestId));

        if (count == null || count <= 0) throw new ServiceException(ResultCode.CONTEST_NOT_HAS_QUESTION);

        contest.setStatus(Constants.TRUE);
        contestCacheManager.addCache(contest);
        return contestMapper.updateById(contest);
    }

    @Override
    public int cancelPublish(Long contestId) {
        Contest contest = getContest(contestId);
        checkContest(contest);
        if (contest.getEndTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.CONTEST_IS_FINISH);
        }
        contest.setStatus(Constants.FALSE);
        contestCacheManager.deleteCache(contestId);
        return contestMapper.updateById(contest);
    }

    private void checkContestSaveParams(ContestAddRequest contestAddRequest, Long contestId) {
        //1、竞赛标题是否重复进行判断   2、竞赛开始、结束时间进行判断
        List<Contest> contestList = contestMapper
                .selectList(new LambdaQueryWrapper<Contest>()
                        .eq(Contest::getTitle, contestAddRequest.getTitle())
                        .ne(contestId != null, Contest::getContestId, contestId));
        if (CollectionUtil.isNotEmpty(contestList)) {
            throw new ServiceException(ResultCode.FAILED_ALREADY_EXISTS);
        }
        if (contestAddRequest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.CONTEST_START_TIME_BEFORE_CURRENT_TIME);  //竞赛开始时间不能早于当前时间
        }
        if (contestAddRequest.getStartTime().isAfter(contestAddRequest.getEndTime())) {
            throw new ServiceException(ResultCode.CONTEST_START_TIME_AFTER_END_TIME);
        }
    }

    /**
     * 检查竞赛信息
     *
     * @param contest 竞赛信息
     */
    private void checkContest(Contest contest) {
        if (contest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.CONTEST_STARTED);
        }
    }

    /**
     * 保存竞赛题目
     *
     * @param contest       竞赛信息
     * @param questionIdSet 竞赛题目id集合
     * @return 保存结果
     */
    private boolean saveContestQuestion(Contest contest, Set<Long> questionIdSet) {
        int num = 1;
        List<ContestQuestion> contestQuestionList = new ArrayList<>();
        for (Long questionId : questionIdSet) {
            ContestQuestion contestQuestion = new ContestQuestion();
            contestQuestion.setContestId(contest.getContestId());
            contestQuestion.setQuestionId(questionId);
            contestQuestion.setDisplayOrder(num++);
            contestQuestionList.add(contestQuestion);
        }
        return saveBatch(contestQuestionList);
    }

    /**
     * 根据竞赛id获取竞赛信息
     *
     * @param contestId 获取竞赛
     * @return 竞赛信息
     */
    private Contest getContest(Long contestId) {
        Contest contest = contestMapper.selectById(contestId);
        if (contest == null) throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        return contest;
    }
}
