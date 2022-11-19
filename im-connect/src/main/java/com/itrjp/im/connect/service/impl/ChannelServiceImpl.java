package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.ChannelService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.proto.dto.MessageProto;
import com.itrjp.im.proto.service.DispatchGrpc;
import com.itrjp.im.proto.service.DispatchRpcService;
import com.itrjp.im.proto.service.MessageGrpc;
import com.itrjp.im.proto.service.MessageRpcService;
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


    @GrpcClient("im-stat")
    private DispatchGrpc.DispatchBlockingStub dispatchStub;
    @GrpcClient("im-message")
    private MessageGrpc.MessageBlockingStub messageBlockingStub;


    public ChannelServiceImpl(ConsulDiscoveryProperties consulDiscoveryProperties, ChannelsHub channelsHub, Executor executor) {
        this.consulDiscoveryProperties = consulDiscoveryProperties;
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
        DispatchRpcService.OnlineRequest request = DispatchRpcService.OnlineRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchRpcService.DispatchResponse online = dispatchStub.online(request);
        System.out.println(online);
        // 通知消息服务
        MessageRpcService.OnNoticeResponse noticeResponse = messageBlockingStub.onNotice(MessageRpcService.EventRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(MessageProto.EventType.join)
                .build());
    }

    @Override
    public void onClose(WebSocketClient client) {
        // 频道ID
        String channelId = client.getChannelId();
        if (channelId == null) {
            return;
        }
        Map<String, List<String>> parameters = client.getParameters();
        String uid = parameters.get("uid").get(0);
        // 通知状态服务
        DispatchRpcService.OfflineRequest request = DispatchRpcService.OfflineRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchRpcService.DispatchResponse offline = dispatchStub.offline(request);

        // 通知消息服务
        MessageRpcService.OnNoticeResponse noticeResponse = messageBlockingStub.onNotice(MessageRpcService.EventRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(MessageProto.EventType.leave)
                .build());
    }
}
