package io.github.zhc.dev.job.service;

import io.github.zhc.dev.job.model.entity.message.Message;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:58
 */
public interface MessageService {
    boolean batchInsert(List<Message> messageTextList);
}
