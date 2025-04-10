package io.github.zhc.dev.job.service;

import io.github.zhc.dev.job.model.entity.message.MessageText;

import java.util.List;

/**
 * @author zhc-dev
 * @data 2025/4/10 22:59
 */
public interface MessageTextService {
    boolean batchInsert(List<MessageText> messageTextList);
}
