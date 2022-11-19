package com.itrjp.im.message.service.impl;

import com.itrjp.im.message.service.MessageService;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.dto.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itrjp.common.consts.KafkaConstant.MESSAGE_JOIN_LEAVE_TOPIC;
import static com.itrjp.common.consts.KafkaConstant.MESSAGE_TOPIC;

/**
 * 消息 service
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:49
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final List<MessageFilter> messageFilterList;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public MessageServiceImpl(List<MessageFilter> messageFilterList, KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.messageFilterList = messageFilterList;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void handlerMessage(String channelId, String userId, String message, long msgId) {
        logger.info("handler message, channelId: {}, userId:{}, message: {}", channelId, userId, message);
        // 消息过滤
        boolean filter = filter(message);
        if (!filter) {
            return;
        }
        // 消息投递给connect 进行广播
        // connect
        kafkaTemplate.send(MESSAGE_TOPIC, channelId, MessageProto.Message.newBuilder()
                .setChannelId(channelId)
                .setContent(message)
                .setMessageId(msgId)
                .setTimestamp(System.currentTimeMillis())
                .build().toByteArray());
        // storage
    }

    @Override
    public void handlerJoinLeave(String channelId, String userId, MessageProto.EventType type) {

        // TODO 上下线限流
        //消息投递
        kafkaTemplate.send(MESSAGE_JOIN_LEAVE_TOPIC, channelId, MessageProto.Event.newBuilder()
                .setType(type)
                .setUserId(userId)
                .setChannelId(channelId).build().toByteArray());

    }

    private boolean filter(String message) {

        for (MessageFilter filter : messageFilterList) {
            if (filter.match(MessageFilter.MessageFilterType.black)) {
                return filter.doFilter(message);
            }
        }
        return false;
    }
}
