package io.github.zhc.dev.job.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.zhc.dev.common.core.constants.CacheConstants;
import io.github.zhc.dev.common.core.constants.Constants;
import io.github.zhc.dev.job.mapper.contest.ContestMapper;
import io.github.zhc.dev.job.mapper.message.MessageTextMapper;
import io.github.zhc.dev.job.mapper.user.UserContestMapper;
import io.github.zhc.dev.job.mapper.user.UserSubmitMapper;
import io.github.zhc.dev.job.model.entity.contest.Contest;
import io.github.zhc.dev.job.model.entity.message.Message;
import io.github.zhc.dev.job.model.entity.message.MessageText;
import io.github.zhc.dev.job.model.entity.user.UserScore;
import io.github.zhc.dev.job.model.vo.MessageTextVO;
import io.github.zhc.dev.job.service.MessageService;
import io.github.zhc.dev.job.service.MessageTextService;
import io.github.zhc.dev.redis.service.RedisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:42
 */
@Slf4j
@Component
public class ContestXxlJob {

    @Resource
    private ContestMapper contestMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private MessageTextService messageTextService;

    @Resource
    private MessageService messageService;

    @Resource
    private UserSubmitMapper userSubmitMapper;

    @Resource
    private MessageTextMapper messageTextMapper;

    @Resource
    private UserContestMapper userContestMapper;


    @XxlJob("examListOrganizeHandler")
    public void examListOrganizeHandler() {
        //  统计哪些竞赛应该存入未完赛的列表中  哪些竞赛应该存入历史竞赛列表中   统计出来了之后，再存入对应的缓存中
        log.info("*** examListOrganizeHandler ***");
        List<Contest> unFinishList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                .gt(Contest::getEndTime, LocalDateTime.now())
                .eq(Contest::getStatus, Constants.TRUE)
                .orderByDesc(Contest::getCreateTime));
        refreshCache(unFinishList, CacheConstants.CONTEST_UNFINISHED_LIST_KEY);

        List<Contest> historyList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                .select(Contest::getContestId, Contest::getTitle, Contest::getStartTime, Contest::getEndTime)
                .le(Contest::getEndTime, LocalDateTime.now())
                .eq(Contest::getStatus, Constants.TRUE)
                .orderByDesc(Contest::getCreateTime));

        refreshCache(historyList, CacheConstants.CONTEST_HISTORY_LIST_KEY);
        log.info("*** examListOrganizeHandler 统计结束 ***");
    }

    @XxlJob("examResultHandler")
    //
    public void examResultHandler() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusDateTime = now.minusDays(1);
        List<Contest> examList = contestMapper.selectList(new LambdaQueryWrapper<Contest>()
                .select(Contest::getContestId, Contest::getTitle)
                .eq(Contest::getStatus, Constants.TRUE)
                .ge(Contest::getEndTime, minusDateTime)
                .le(Contest::getEndTime, now));
        if (CollectionUtil.isEmpty(examList)) return;
        Set<Long> examIdSet = examList.stream().map(Contest::getContestId).collect(Collectors.toSet());
        List<UserScore> userScoreList = userSubmitMapper.selectUserScoreList(examIdSet);
        Map<Long, List<UserScore>> userScoreMap = userScoreList.stream().collect(Collectors.groupingBy(UserScore::getContestId));
        createMessage(examList, userScoreMap);
    }

    private void createMessage(List<Contest> examList, Map<Long, List<UserScore>> userScoreMap) {
        List<MessageText> messageTextList = new ArrayList<>();
        List<Message> messageList = new ArrayList<>();
        for (Contest exam : examList) {
            Long examId = exam.getContestId();
            List<UserScore> userScoreList = userScoreMap.get(examId);
            int totalUser = userScoreList.size();
            int examRank = 1;
            for (UserScore userScore : userScoreList) {
                String msgTitle = exam.getTitle() + "——排名情况";
                String msgContent = "您所参与的竞赛：" + exam.getTitle()
                        + "，本次参与竞赛一共" + totalUser + "人， 您排名第" + examRank + "名！";
                userScore.setContestRank(examRank);
                MessageText messageText = new MessageText();
                messageText.setMessageTitle(msgTitle);
                messageText.setMessageContent(msgContent);
                messageText.setCreateBy(Constants.SYSTEM_USER_ID);
                messageTextList.add(messageText);
                Message message = new Message();
                message.setSendId(Constants.SYSTEM_USER_ID);
                message.setCreateBy(Constants.SYSTEM_USER_ID);
                message.setRecId(userScore.getUserId());
                messageList.add(message);
                examRank++;
            }
            userContestMapper.updateUserScoreAndRank(userScoreList);
            redisService.rightPushAll(getContestRankListKey(examId), userScoreList);
        }
        messageTextService.batchInsert(messageTextList);
        Map<String, MessageTextVO> messageTextVOMap = new HashMap<>();
        for (int i = 0; i < messageTextList.size(); i++) {
            MessageText messageText = messageTextList.get(i);
            MessageTextVO messageTextVO = new MessageTextVO();
            BeanUtil.copyProperties(messageText, messageTextVO);
            String msgDetailKey = getMsgDetailKey(messageText.getTextId());
            messageTextVOMap.put(msgDetailKey, messageTextVO);
            Message message = messageList.get(i);
            message.setTextId(messageText.getTextId());
        }
        messageService.batchInsert(messageList);
        //redis 操作
        Map<Long, List<Message>> userMsgMap = messageList.stream().collect(Collectors.groupingBy(Message::getRecId));
        Iterator<Map.Entry<Long, List<Message>>> iterator = userMsgMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<Message>> entry = iterator.next();
            Long recId = entry.getKey();
            String userMsgListKey = getUserMsgListKey(recId);
            List<Long> userMsgTextIdList = entry.getValue().stream().map(Message::getTextId).toList();
            redisService.rightPushAll(userMsgListKey, userMsgTextIdList);
        }
        redisService.multiSet(messageTextVOMap);
    }


    public void refreshCache(List<Contest> examList, String examListKey) {
        if (CollectionUtil.isEmpty(examList)) {
            return;
        }

        Map<String, Contest> examMap = new HashMap<>();
        List<Long> examIdList = new ArrayList<>();
        for (Contest exam : examList) {
            examMap.put(getDetailKey(exam.getContestId()), exam);
            examIdList.add(exam.getContestId());
        }
        redisService.multiSet(examMap);  //刷新详情缓存
        redisService.deleteObject(examListKey);
        redisService.rightPushAll(examListKey, examIdList);      //刷新列表缓存
    }

    private String getDetailKey(Long examId) {
        return CacheConstants.CONTEST_DETAIL_KEY_PREFIX + examId;
    }

    private String getUserMsgListKey(Long userId) {
        return CacheConstants.USER_MESSAGE_LIST_KEY_PREFIX + userId;
    }

    private String getMsgDetailKey(Long textId) {
        return CacheConstants.MESSAGE_DETAIL_KEY_PREFIX + textId;
    }

    private String getContestRankListKey(Long examId) {
        return CacheConstants.CONTEST_RANK_LIST_KEY_PREFIX + examId;
    }
}
