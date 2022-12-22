package com.itrjp.im.message.grpc;

import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.message.service.impl.ChannelsServiceImpl;
import com.itrjp.im.proto.service.ChannelGrpc;
import com.itrjp.im.proto.service.ChannelRpcService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/22 07:24
 */
@Service
@GrpcService
@Slf4j
@RequiredArgsConstructor
public class ChannelGrpcImpl extends ChannelGrpc.ChannelImplBase {
    private final ChannelsServiceImpl channelService;

    @Override
    public void get(ChannelRpcService.GetRequest request, StreamObserver<ChannelRpcService.Response> responseObserver) {
        Channels channels = channelService.getByChannelId(request.getChannelId());
        if (channels == null) {
            responseObserver.onNext(ChannelRpcService.Response.newBuilder()
                    .setCode(404)
                    .setMessage("频道未找到")
                    .build());
        } else {
            responseObserver.onNext(ChannelRpcService.Response.newBuilder()
                    .setCode(200)
                    .setChannelId(channels.getChannelId())
                    .setFilterType(channels.getFilterType().getValue())
                    .setLimit(channels.getChannelLimit())
                    .build());

        }
        responseObserver.onCompleted();
    }
}
