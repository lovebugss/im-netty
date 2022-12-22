package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.service.ChannelService;
import com.itrjp.im.proto.service.ChannelGrpc;
import com.itrjp.im.proto.service.ChannelRpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {

    @GrpcClient("im-message")
    private ChannelGrpc.ChannelBlockingStub channelBlockingStub;

    @Override
    public void checkChannelId(String channelId) {
        Channel channel = getChannel(channelId);
        if (channel == null) {
            throw new ChannelNotFoundException();
        }
    }

    @Override
    public Channel getChannel(String channelId) {
        ChannelRpcService.Response response = channelBlockingStub.get(ChannelRpcService.GetRequest
                .newBuilder()
                .setChannelId(channelId)
                .build());
        if (response.getCode() == 200) {
            return Channel.builder()
                    .channelId(channelId)
                    .build();
        }
        return null;
    }
}
