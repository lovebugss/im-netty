package com.itrjp.im.message.service.impl;

import com.google.protobuf.ByteString;
import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.service.*;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.*;
import com.itrjp.im.uid.UidRequest;
import com.itrjp.im.uid.UidResponse;
import com.itrjp.im.uid.UidServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itrjp.common.consts.KafkaConstant.CONNECT_TOPIC_PREFIX;

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

    @GrpcClient("im-stat")
    private ConnectNodeServiceGrpc.ConnectNodeServiceBlockingStub connectNodeServiceBlockingStub;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final MessageStorageService messageStorageService;
    private final MessageHistoryService messageHistoryService;

    private final IChannelsService channelService;

    private final MessageFilterService messageFilterService;


    @Override
    public String handlerMessage(Message message) {
        String channelId = message.getChannelId();
        String userId = message.getUserId();
        logger.info("handler message, channelId: {}, userId:{}, message: {}", channelId, userId, message.getContent());
        String messageId = createMessageId(channelId);

        ChannelInfo channelInfo = channelService.getByChannelId(channelId).orElseThrow();
        // 消息过滤
        boolean filter = messageFilterService.filter(message, MessageFilterType.valueOfCode(Integer.valueOf(channelInfo.getFilterType())));
        if (!filter) {
            // 存储不合法的消息
            messageStorageService.saveInvalidMessage(channelId, userId, message.getContent());
            return messageId + "";
        }
        ByteString messageByteStr = Message.newBuilder()
                .setChannelId(channelId)
                .setUserId(userId)
                .setContent(message.getContent())
                .setMessageId(messageId)
                .setTimestamp(System.currentTimeMillis())
                .build().toByteString();
        // 消息投递给connect 进行广播
        sendMessageToConnect(channelId, userId, messageByteStr, DataType.MSG);
        // storage
        messageStorageService.saveMessage(channelId, userId, message.getContent(), messageId);
        messageHistoryService.add(channelId, userId, message.getContent(), messageId);
        return messageId + "";
    }

    /**
     * 发送到connect节点, 然后进行广播
     *
     * @param channelId channel id
     * @param userId    user id
     * @param message   message
     * @param type      type
     */
    private void sendMessageToConnect(String channelId, String userId, ByteString message, DataType type) {
        ChannelNodeResponse channelNode = connectNodeServiceBlockingStub.getConnectNode(ChannelNodeRequest.newBuilder()
                .setChannelId(channelId).build());
        if (channelNode == null) {
            logger.error("消息投递失败, connect节点为空, channelId: {}", channelId);
            return;
        }
        List<ChannelNodeInfo> channelNodeList = channelNode.getChannelNodeList();
        for (ChannelNodeInfo channelNodeInfo : channelNodeList) {
            // connect
            logger.info("发送消息到Kafka, topic: {}, message: {}", CONNECT_TOPIC_PREFIX + channelNodeInfo.getNodeId(), message);
            kafkaTemplate.send(CONNECT_TOPIC_PREFIX + channelNodeInfo.getNodeId(), channelId, KafkaMessage.newBuilder()
                    .setTimestamp(System.currentTimeMillis())
                    .setFrom(userId)
                    .setTo(channelId)
                    .setDataType(type)
                    .setData(message)
                    .setNodeId(channelNodeInfo.getNodeId())
                    .build().toByteArray());
        }
    }

    private String createMessageId(String channelId) {
        // 生成全局唯一ID
        UidResponse response = uidBlockingStub.genUid(UidRequest.newBuilder().setChannelId(channelId).build());
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
        sendMessageToConnect(channelId, userId, Event.newBuilder()
                .setType(type)
                .setUserId(userId)
                .setChannelId(channelId).build().toByteString(), DataType.EVENT);

    }
}
