package com.itrjp.im.message.service;

import com.itrjp.im.message.entity.MessageHistory;

import java.util.List;

/**
 * 消息历史记录
 *
 * @author renjp
 */
public interface MessageHistoryService {

    List<MessageHistory> list();

    void add(String channelId, String userId, String message, String messageId);
}
