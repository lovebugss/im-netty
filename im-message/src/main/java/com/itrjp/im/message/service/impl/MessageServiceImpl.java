package com.itrjp.im.message.service.impl;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.service.IChannelsService;
import com.itrjp.im.message.service.MessageHistoryService;
import com.itrjp.im.message.service.MessageService;
import com.itrjp.im.message.service.MessageStorageService;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.*;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
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
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final List<MessageFilter> messageFilterList;
    @GrpcClient("im-uid")
    private UidServiceGrpc.UidServiceBlockingStub uidBlockingStub;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final MessageStorageService messageStorageService;
    private final MessageHistoryService messageHistoryService;

    private final IChannelsService channelService;


    @Override
    public String handlerMessage(String channelId, String userId, String message) {
        logger.info("handler message, channelId: {}, userId:{}, message: {}", channelId, userId, message);
        String messageId = createMessageId();

        ChannelInfo channelInfo = channelService.getByChannelId(channelId).orElseThrow();
        // 消息过滤
        boolean filter = filter(message, MessageFilterType.valueOf(channelInfo.getFilterType()));
        if (!filter) {
            // 存储不合法的消息
            messageStorageService.saveInvalidMessage(channelId, userId, message);
            return messageId + "";
        }
        // 消息投递给connect 进行广播
        // connect
        kafkaTemplate.send(MESSAGE_TOPIC, channelId, Data.newBuilder()
                .setChannelId(channelId)
                .setContent(message)
                .setMessageId(messageId)
                .setTimestamp(System.currentTimeMillis())
                .build().toByteArray());
        // storage
        messageStorageService.saveMessage(channelId, userId, message, messageId);
        messageHistoryService.add(channelId, userId, message, messageId);
        return messageId + "";
    }

    private String createMessageId() {
        // 生成全局唯一ID
        UidResponse response = uidBlockingStub.genUid(UidRequest.newBuilder().build());
        // TODO 当生成id 失败时, 如何处理
        if (response.getCode() != 200) {
            return String.valueOf(System.currentTimeMillis());
        }
        return response.getUid();
    }

    @Override
    public void handlerJoinLeave(String channelId, String userId, EventType type) {

        // TODO 上下线限流
        //消息投递
        kafkaTemplate.send(MESSAGE_JOIN_LEAVE_TOPIC, channelId, Event.newBuilder()
                .setType(type)
                .setUserId(userId)
                .setChannelId(channelId).build().toByteArray());

    }

    private boolean filter(String message, MessageFilterType filterType) {
        // 查询当前房间配置的过滤器
        for (MessageFilter filter : messageFilterList) {
            if (filter.match(filterType)) {
                return filter.doFilter(message);
            }
        }
        return false;
    }
}
