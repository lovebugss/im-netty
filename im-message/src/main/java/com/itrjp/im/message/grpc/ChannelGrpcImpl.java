package com.itrjp.im.message.grpc;

import com.itrjp.im.message.service.impl.ChannelsServiceImpl;
import com.itrjp.im.proto.ChannelGrpc;
import com.itrjp.im.proto.ChannelProto;
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
    public void getChannelInfo(ChannelProto.GetRequest request, StreamObserver<ChannelProto.GetResponse> responseObserver) {
        channelService.getByChannelId(request.getChannelId())
                .ifPresentOrElse(
                        (channelInfo) -> responseObserver.onNext(ChannelProto.GetResponse.newBuilder()
                                .setCode(200)
                                .setChannelInfo(channelInfo)
                                .build()),
                        () -> responseObserver.onNext(ChannelProto.GetResponse.newBuilder()
                                .setCode(404)
                                .setMessage("频道未找到")
                                .build()));

        responseObserver.onCompleted();
    }
}
