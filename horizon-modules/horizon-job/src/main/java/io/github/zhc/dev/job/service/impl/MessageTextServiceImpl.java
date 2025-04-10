package io.github.zhc.dev.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhc.dev.job.mapper.message.MessageTextMapper;
import io.github.zhc.dev.job.model.entity.message.MessageText;
import io.github.zhc.dev.job.service.MessageTextService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 23:01
 */
@Service
public class MessageTextServiceImpl extends ServiceImpl<MessageTextMapper, MessageText> implements MessageTextService {
    @Override
    public boolean batchInsert(List<MessageText> messageTextList) {
        return saveBatch(messageTextList);
    }
}
