package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.proto.dto.MessageProto;
import com.itrjp.im.proto.service.MessageGrpc;
import com.itrjp.im.proto.service.MessageRpcService;
import com.itrjp.im.proto.service.UidGrpc;
import com.itrjp.im.proto.service.UidRpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:05
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final ChannelsHub channelsHub;

    @GrpcClient("im-message")
    private MessageGrpc.MessageBlockingStub messageBlockingStub;


    public MessageServiceImpl(ChannelsHub channelsHub) {
        this.channelsHub = channelsHub;
    }

    @Override
    public void onMessage(WebSocketClient channel, MessageProto.Packet data) {
        MessageProto.Message message = data.getMessage();
        // 直接交给im-message 服务进行处理
        Map<String, List<String>> parameters = channel.getParameters();

        MessageRpcService.MessageRequest messageRequest = MessageRpcService.MessageRequest.newBuilder()
                .setChannelId(channel.getChannelId())
                .setUserId(parameters.get("uid").get(0))
                .setContent(message.getContent())
                .setFrom(parameters.get("uid").get(0))
                .setTo(channel.getChannelId())
                .setTimestamp(System.currentTimeMillis())
                .build();
        // 消息投递给im-message服务
        messageBlockingStub.onMessage(messageRequest);
    }

    @Override
    public void broadcastMessage(String channelId, MessageProto.Message message) {
        MessageProto.Packet packet = MessageProto.Packet.newBuilder()
                .setDataType(MessageProto.DataType.msg)
                .setMessage(message)
                .setTimestamp(message.getTimestamp())
                .build();
        broadcast(channelId, packet);
    }

    private void broadcast(String channelId, MessageProto.Packet packet) {
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
    public void broadcastEvent(String channelId, MessageProto.Event event) {

        MessageProto.Packet build = MessageProto.Packet.newBuilder()
                .setDataType(MessageProto.DataType.notion)
                .setEvent(event)
                .setTimestamp(event.getTimestamp())
                .build();
        broadcast(channelId, build);
    }
}
