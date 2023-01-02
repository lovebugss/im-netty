package com.itrjp.im.api.service;


import com.itrjp.im.api.entity.ChannelNodeInfo;
import com.itrjp.im.api.entity.ConnectNode;

import java.util.List;

public interface ConnectNodeService {
    /**
     * 获取最佳节点
     *
     * @param channelId
     * @return
     */
    List<ChannelNodeInfo> getBestNode(String channelId);

    List<ConnectNode> getAvailableList();

    void offline(ConnectNode connectNode);

    void online(ConnectNode connectNode);
}
