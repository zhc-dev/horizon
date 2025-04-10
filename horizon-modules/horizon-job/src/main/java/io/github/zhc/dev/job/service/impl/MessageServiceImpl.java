package io.github.zhc.dev.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhc.dev.job.mapper.message.MessageMapper;
import io.github.zhc.dev.job.model.entity.message.Message;
import io.github.zhc.dev.job.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 23:00
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Override
    public boolean batchInsert(List<Message> messageTextList) {
        return saveBatch(messageTextList);
    }
}
