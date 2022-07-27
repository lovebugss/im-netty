package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.ChannelService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.proto.dispatcher.DispatchGrpc;
import com.itrjp.im.proto.dispatcher.DispatchProto;
import com.itrjp.im.proto.message.MessageGrpc;
import com.itrjp.im.proto.message.MessageProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 21:30
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ConsulDiscoveryProperties consulDiscoveryProperties;
    private final ChannelsHub channelsHub;
    private final Executor executor;


    @GrpcClient("im-stat")
    private DispatchGrpc.DispatchBlockingStub dispatchStub;
    @GrpcClient("im-message")
    private MessageGrpc.MessageBlockingStub messageBlockingStub;


    public ChannelServiceImpl(ConsulDiscoveryProperties consulDiscoveryProperties, ChannelsHub channelsHub, Executor executor) {
        this.consulDiscoveryProperties = consulDiscoveryProperties;
        this.channelsHub = channelsHub;
        this.executor = executor;
    }

    @Override
    public void onOpen(WebSocketClient client) {
        String channelId = client.getChannelId();
        if (channelId == null) {
            return;
        }
        Map<String, List<String>> parameters = client.getParameters();
        String uid = parameters.get("uid").get(0);
        // 通知状态服务
        DispatchProto.DispatchRequest request = DispatchProto.DispatchRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchProto.ApiResponse online = dispatchStub.online(request);
        System.out.println(online);
        // 通知消息服务
        MessageProto.ApiResponse apiResponse = messageBlockingStub.onJoinLeave(MessageProto.JoinLeaveRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(MessageProto.OperateType.leave)
                .build());
    }

    @Override
    public void onClose(WebSocketClient client) {
        System.out.println("on close");
        // 频道ID
        String channelId = client.getChannelId();
        if (channelId == null) {
            return;
        }
        Map<String, List<String>> parameters = client.getParameters();
        String uid = parameters.get("uid").get(0);
        // 通知状态服务
        DispatchProto.DispatchRequest request = DispatchProto.DispatchRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchProto.ApiResponse offline = dispatchStub.offline(request);
        System.out.println(offline);

        // 通知消息服务
        MessageProto.ApiResponse apiResponse = messageBlockingStub.onJoinLeave(MessageProto.JoinLeaveRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(MessageProto.OperateType.leave)
                .build());
    }
}
