package com.itrjp.im.message.service.impl;

import com.itrjp.im.message.service.MessageService;
import com.itrjp.im.message.service.MessageStorageService;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.dto.MessageProto;
import com.itrjp.im.proto.service.UidGrpc;
import com.itrjp.im.proto.service.UidRpcService;
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
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final List<MessageFilter> messageFilterList;
    @GrpcClient("im-uid")
    private UidGrpc.UidBlockingStub uidBlockingStub;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final MessageStorageService messageStorageService;

    public MessageServiceImpl(List<MessageFilter> messageFilterList, KafkaTemplate<String, byte[]> kafkaTemplate, MessageStorageService messageStorageService) {
        this.messageFilterList = messageFilterList;
        this.kafkaTemplate = kafkaTemplate;
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void handlerMessage(String channelId, String userId, String message) {
        logger.info("handler message, channelId: {}, userId:{}, message: {}", channelId, userId, message);
        long messageId = createMessageId();
        // 消息过滤
        boolean filter = filter(message);
        if (!filter) {
            // 存储不合法的消息
            messageStorageService.saveInvalidMessage(channelId, userId, message);
            return;
        }
        // 消息投递给connect 进行广播
        // connect
        kafkaTemplate.send(MESSAGE_TOPIC, channelId, MessageProto.Message.newBuilder()
                .setChannelId(channelId)
                .setContent(message)
                .setMessageId(messageId)
                .setTimestamp(System.currentTimeMillis())
                .build().toByteArray());
        // storage
        messageStorageService.saveMessage(channelId, userId, message, messageId);
    }

    private long createMessageId() {
        // 生成全局唯一ID
        UidRpcService.UidResponse response = uidBlockingStub.genUid(UidRpcService.UidRequest.newBuilder().build());
        // TODO 当生成id 失败时, 如何处理
        if (response.getCode() != 200) {
            return System.currentTimeMillis();
        }
        return response.getUid();
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
