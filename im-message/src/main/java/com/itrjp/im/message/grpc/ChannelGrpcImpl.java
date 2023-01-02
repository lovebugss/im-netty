package com.itrjp.im.message.grpc;

import com.itrjp.im.message.service.IChannelsService;
import com.itrjp.im.proto.ChannelServiceGrpc;
import com.itrjp.im.proto.GetRequest;
import com.itrjp.im.proto.GetResponse;
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
public class ChannelGrpcImpl extends ChannelServiceGrpc.ChannelServiceImplBase {
    private final IChannelsService channelService;

    @Override
    public void getChannelInfo(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        channelService.getByChannelId(request.getChannelId())
                .ifPresentOrElse(
                        (channelInfo) -> responseObserver.onNext(GetResponse.newBuilder()
                                .setCode(200)
                                .setChannelInfo(channelInfo)
                                .build()),
                        () -> responseObserver.onNext(GetResponse.newBuilder()
                                .setCode(404)
                                .setMessage("频道未找到")
                                .build()));

        responseObserver.onCompleted();
    }
}
