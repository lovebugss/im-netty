package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.pojo.param.CreateChannelParam;
import com.itrjp.im.api.service.ChannelService;
import com.itrjp.im.proto.message.ChannelInfo;
import com.itrjp.im.proto.message.ChannelServiceGrpc;
import com.itrjp.im.proto.message.GetRequest;
import com.itrjp.im.proto.message.GetResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @GrpcClient("im-message")
    private ChannelServiceGrpc.ChannelServiceBlockingStub channelBlockingStub;

    @Override
    public void checkChannelId(String channelId) {
        getChannel(channelId)
                .orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public Optional<ChannelInfo> getChannel(String channelId) {
        GetResponse response = channelBlockingStub.getChannelInfo(GetRequest
                .newBuilder()
                .setChannelId(channelId)
                .build());
        if (response.getCode() == 200) {
            return Optional.of(response.getChannelInfo());
        }
        return Optional.empty();
    }

    @Override
    public com.itrjp.im.api.entity.Channel createChannel(CreateChannelParam param) {

        return null;
    }

    @Override
    public void checkUserInChannel(String channelId, String userId) {

    }

    @Override
    public void muteUser(String channelId, String userId) {

    }

    @Override
    public void unMuteUser(String channelId, String userId) {

    }
}
