package com.itrjp.im.stat.grpc;


import com.itrjp.im.proto.stat.*;
import com.itrjp.im.stat.service.ChannelStatService;
import com.itrjp.im.stat.service.NodeStatService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 22:26
 */
@Service
@GrpcService
public class DispatcherImpl extends StatServiceGrpc.StatServiceImplBase {
    private final Logger logger = LoggerFactory.getLogger(DispatcherImpl.class);
    private final ChannelStatService channelStatService;
    private final NodeStatService nodeStatService;

    public DispatcherImpl(ChannelStatService channelStatService, NodeStatService nodeStatService) {
        this.channelStatService = channelStatService;
        this.nodeStatService = nodeStatService;
    }

    @Override
    public void online(OnlineRequest request, StreamObserver<DispatchResponse> responseObserver) {
        String channelId = request.getChannelId();
        String userId = request.getUserId();
        logger.info("用户上线, 当前频道: {}, 用户id: {}", channelId, userId);
        channelStatService.online(channelId, userId, request.getSessionId());
        nodeStatService.connected(request.getNodeId(), channelId, userId, request.getSessionId());
        responseObserver.onNext(DispatchResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void offline(OfflineRequest request, StreamObserver<DispatchResponse> responseObserver) {
        String channelId = request.getChannelId();
        String userId = request.getUserId();
        logger.info("用户下线, 当前频道: {}, 用户id: {}", channelId, userId);
        channelStatService.offline(channelId, userId, request.getSessionId());
        nodeStatService.disConnected(request.getNodeId(), channelId, userId, request.getSessionId());
        responseObserver.onNext(DispatchResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectInfo(GetConnectInfoRequest request, StreamObserver<GetConnectInfoResponse> responseObserver) {
        super.getConnectInfo(request, responseObserver);
    }
}
