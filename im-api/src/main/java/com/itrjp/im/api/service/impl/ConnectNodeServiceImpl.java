package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.BizException;
import com.itrjp.im.api.service.ConnectNodeService;
import com.itrjp.im.proto.ChannelNodeInfo;
import com.itrjp.im.proto.ChannelNodeRequest;
import com.itrjp.im.proto.ChannelNodeResponse;
import com.itrjp.im.proto.ChannelNodeServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectNodeServiceImpl implements ConnectNodeService {


    @GrpcClient("im-dispatcher")
    private ChannelNodeServiceGrpc.ChannelNodeServiceBlockingStub channelNodeServiceBlockingStub;


    @Override
    public List<ChannelNodeInfo> getBestNode(String channelId) {

        ChannelNodeResponse bestChannelNode = channelNodeServiceBlockingStub.getBestChannelNode(ChannelNodeRequest.newBuilder()
                .setChannelId(channelId)
                .build());
        int code = bestChannelNode.getCode();
        if (code != 200) {
            throw new BizException(4001, "获取最佳节点失败");
        }
        return bestChannelNode.getChannelNodeList();
    }

}
