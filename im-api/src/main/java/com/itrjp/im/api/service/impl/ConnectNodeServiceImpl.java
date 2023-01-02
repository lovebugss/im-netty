package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.BizException;
import com.itrjp.im.api.entity.ChannelNodeInfo;
import com.itrjp.im.api.entity.ConnectNode;
import com.itrjp.im.api.service.ConnectNodeService;
import com.itrjp.im.proto.ChannelNodeRequest;
import com.itrjp.im.proto.ChannelNodeResponse;
import com.itrjp.im.proto.ConnectNodeResponse;
import com.itrjp.im.proto.ConnectNodeServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConnectNodeServiceImpl implements ConnectNodeService {


    @GrpcClient("im-stat")
    private ConnectNodeServiceGrpc.ConnectNodeServiceBlockingStub blockingStub;

    @Override
    public List<ConnectNode> getAvailableList() {
        ChannelNodeResponse response = blockingStub.getAvailableList(ChannelNodeRequest.newBuilder().build());
        return response.getChannelNodeList().stream().map(connectNode -> {
            ConnectNode node = new ConnectNode();
            node.setAddress(connectNode.getAddress());
            node.setPort(connectNode.getPort());
            node.setId(connectNode.getNodeId());
            node.setWsPort(connectNode.getPort());
            return node;
        }).toList();
    }

    @Override
    public void offline(ConnectNode connectNode) {
        ConnectNodeResponse response = blockingStub.stop(com.itrjp.im.proto.ChannelNodeInfo.newBuilder()
                .setNodeId(connectNode.getId())
                .setProtocol("ws")
                .setAddress(connectNode.getAddress())
                .setPort(connectNode.getWsPort())
                .build());
        log.info("下线节点:{}", response);
    }

    @Override
    public void online(ConnectNode connectNode) {
        ConnectNodeResponse response = blockingStub.startUp(com.itrjp.im.proto.ChannelNodeInfo.newBuilder()
                .setNodeId(connectNode.getId())
                .setProtocol("ws")
                .setAddress(connectNode.getAddress())
                .setPort(connectNode.getWsPort())
                .build());
        log.info("上线节点:{}", response);
    }

    @Override
    public List<ChannelNodeInfo> getBestNode(String channelId) {

        ChannelNodeResponse bestChannelNode = blockingStub.getBestChannelNode(ChannelNodeRequest.newBuilder()
                .setChannelId(channelId)
                .build());
        int code = bestChannelNode.getCode();
        if (code != 200) {
            log.error("获取最佳节点失败:{}", bestChannelNode);
            throw new BizException(4001, "获取最佳节点失败");
        }
        // 获取最佳节点
        List<com.itrjp.im.proto.ChannelNodeInfo> channelNodeList = bestChannelNode.getChannelNodeList();
        return channelNodeList.stream().map(i -> {
            ChannelNodeInfo channelNodeInfo = new ChannelNodeInfo();
            channelNodeInfo.setProtocol(i.getProtocol());
            channelNodeInfo.setAddress(i.getAddress());
            channelNodeInfo.setPort(i.getPort());
            return channelNodeInfo;
        }).collect(Collectors.toList());
    }

}
