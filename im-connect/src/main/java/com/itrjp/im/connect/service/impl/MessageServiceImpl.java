package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.cache.MuteList;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.proto.message.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ChannelsHub channelsHub;
    private final MuteList muteList;

    @GrpcClient("im-message")
    private MessageServiceGrpc.MessageServiceBlockingStub messageBlockingStub;


    @Override
    public void onMessage(WebSocketClient channel, Message message) {

        // 直接交给im-message 服务进行处理
        Map<String, List<String>> parameters = channel.getParameters();
        // 判断当前用户是否被禁言
        String uid = parameters.get("uid").get(0);
        String channelId = channel.getChannelId();
        if (muteList.contains(channelId, uid)) {
            log.info("用户被禁言, uid: {}, session: {}", uid, channel.getSession());
            return;
        }

        Message messageRequest = Message.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setContent(message.getContent())
                .setTimestamp(System.currentTimeMillis())
                .build();
        // 消息投递给im-message服务
        MessageResponse response = messageBlockingStub.onMessage(messageRequest);
        // TODO 失败后处理
    }

    @Override
    public void broadcastMessage(String channelId, Message data) {
        Packet packet = Packet.newBuilder()
                .setDataType(DataType.MSG)
                .setData(data.toByteString())
                .setTimestamp(System.currentTimeMillis())
                .build();
        log.info("广播消息: channelId: {}, data: {}, message: {}", channelId, packet, data);
        broadcast(channelId, packet);
    }

    private void broadcast(String channelId, Packet packet) {
        WebsocketChannel websocketChannel = channelsHub.get(channelId);
        if (websocketChannel == null) {
            return;
        }
        // 循环广播
        for (WebSocketClient webSocketClient : websocketChannel.getAllClient()) {
            webSocketClient.sendMessage(packet);
        }
    }


    @Override
    public void broadcastEvent(String channelId, Event event) {

        Packet build = Packet.newBuilder()
                .setDataType(DataType.EVENT)
                .setData(event.toByteString())
                .setTimestamp(event.getTimestamp())
                .build();
        broadcast(channelId, build);
    }
}
