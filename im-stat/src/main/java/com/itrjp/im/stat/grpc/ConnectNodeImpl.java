package com.itrjp.im.stat.grpc;

import com.itrjp.im.proto.*;
import com.itrjp.im.stat.service.NodeStatService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 21:09
 */
@Service
@GrpcService
@RequiredArgsConstructor
public class ConnectNodeImpl extends ConnectNodeServiceGrpc.ConnectNodeServiceImplBase {
    private final NodeStatService connectNodeService;

    @Override
    public void startUp(ChannelNodeInfo request, StreamObserver<ConnectNodeResponse> responseObserver) {
        connectNodeService.start(request);
        responseObserver.onNext(ConnectNodeResponse.newBuilder().setCode(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void stop(ChannelNodeInfo request, StreamObserver<ConnectNodeResponse> responseObserver) {
        connectNodeService.stop(request);
        responseObserver.onNext(ConnectNodeResponse.newBuilder().setCode(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getBestChannelNode(ChannelNodeRequest request, StreamObserver<ChannelNodeResponse> responseObserver) {
        // 获取最佳节点
        List<ChannelNodeInfo> bestNode = connectNodeService.getBestNode(request.getChannelId());
        if (bestNode.isEmpty()) {
            responseObserver.onNext(ChannelNodeResponse.newBuilder()
                    .setCode(400)
                    .setMessage("没有可用的服务")
                    .build());
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onNext(ChannelNodeResponse.newBuilder()
                .setCode(200)
                .addAllChannelNode(bestNode)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAvailableList(ChannelNodeRequest request, StreamObserver<ChannelNodeResponse> responseObserver) {
        List<ChannelNodeInfo> availableList = connectNodeService.getAvailableList();
        if (availableList.isEmpty()) {
            responseObserver.onNext(ChannelNodeResponse.newBuilder()
                    .setCode(400)
                    .setMessage("没有可用的服务")
                    .build());
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onNext(ChannelNodeResponse.newBuilder()
                .setCode(200)
                .addAllChannelNode(availableList)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getConnectNode(ChannelNodeRequest request, StreamObserver<ChannelNodeResponse> responseObserver) {
        connectNodeService.getConnectNodeByChannelId(request.getChannelId())
                .ifPresentOrElse(
                channelNodeInfos -> responseObserver.onNext(ChannelNodeResponse.newBuilder()
                        .setCode(200)
                        .addAllChannelNode(channelNodeInfos)
                        .build()),
                () -> responseObserver.onNext(ChannelNodeResponse.newBuilder()
                        .setCode(400)
                        .setMessage("没有可用的服务")
                        .build())
        );
        responseObserver.onCompleted();
    }
}
