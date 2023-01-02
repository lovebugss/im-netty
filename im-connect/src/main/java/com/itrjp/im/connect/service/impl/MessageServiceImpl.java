package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.proto.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:05
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final ChannelsHub channelsHub;

    @GrpcClient("im-message")
    private MessageServiceGrpc.MessageServiceBlockingStub messageBlockingStub;


    public MessageServiceImpl(ChannelsHub channelsHub) {
        this.channelsHub = channelsHub;
    }

    @Override
    public void onMessage(WebSocketClient channel, Message message) {

        // 直接交给im-message 服务进行处理
        Map<String, List<String>> parameters = channel.getParameters();

        Message messageRequest = Message.newBuilder()
                .setChannelId(channel.getChannelId())
                .setUserId(parameters.get("uid").get(0))
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
