package com.itrjp.im.message.service.impl;

import com.itrjp.im.message.entity.MessageHistory;
import com.itrjp.im.message.service.MessageHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHistoryServiceImpl implements MessageHistoryService {

    @Override
    public List<MessageHistory> list() {
        return null;
    }

    @Override
    public void add(String channelId, String userId, String message, long messageId) {

    }
}
