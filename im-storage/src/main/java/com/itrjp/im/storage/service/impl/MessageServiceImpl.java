package com.itrjp.im.storage.service.impl;

import com.itrjp.im.proto.EventType;
import com.itrjp.im.storage.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 消息处理 TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 10:42
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void handlerMessage(String channelId, String content) {
        logger.info("handler message channelId: {}, content: {}", channelId, content);
        // TODO 消息落盘
    }

    @Override
    public void handlerJoinLeaveMessage(String channelId, String userId, EventType type) {
        logger.info("handler join/leave message channelId: {}, userId: {}, type: {}", channelId, userId, type);
        // TODO 消息落盘
    }
}
