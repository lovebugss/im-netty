package com.itrjp.im.stat.grpc;

import com.itrjp.im.proto.dispatcher.DispatchGrpc;
import com.itrjp.im.proto.dispatcher.DispatchProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 22:26
 */
@GrpcService
public class DispatcherImpl extends DispatchGrpc.DispatchImplBase {
    private final Logger logger = LoggerFactory.getLogger(DispatcherImpl.class);

    @Override
    public void online(DispatchProto.DispatchRequest request, StreamObserver<DispatchProto.ApiResponse> responseObserver) {
        String channelId = request.getChannelId();
        String userId = request.getUserId();
        logger.info("用户上线, 当前频道: {}, 用户id: {}", channelId, userId);
        responseObserver.onNext(DispatchProto.ApiResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void offline(DispatchProto.DispatchRequest request, StreamObserver<DispatchProto.ApiResponse> responseObserver) {
        String channelId = request.getChannelId();
        String userId = request.getUserId();
        logger.info("用户下线, 当前频道: {}, 用户id: {}", channelId, userId);
        responseObserver.onNext(DispatchProto.ApiResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectInfo(DispatchProto.DispatchRequest request, StreamObserver<DispatchProto.ApiResponse> responseObserver) {
        super.getConnectInfo(request, responseObserver);
    }
}
