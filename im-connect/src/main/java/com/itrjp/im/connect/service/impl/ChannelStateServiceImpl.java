package com.itrjp.im.connect.service.impl;

import com.itrjp.common.grpc.GrpcService;
import com.itrjp.im.connect.service.ChannelStateService;
import com.itrjp.im.proto.service.ChannelStateGrpc;
import com.itrjp.im.proto.service.ChannelStateRpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ChannelStateServiceImpl implements ChannelStateService {

    @GrpcClient("im-stat")
    private ChannelStateGrpc.ChannelStateBlockingStub statServiceBlockingStub;

    @Override
    public int getChannelConnectCount(String channelId) {
        ChannelStateRpcService.ChannelStateResponse count = statServiceBlockingStub.getChannelConnectCount(ChannelStateRpcService.ChannelRequest.newBuilder()
                .setChannelId(channelId)
                .build());
        if (count.getCode() == 200) {
            return count.getCount();
        }
        return 0;
    }
}
