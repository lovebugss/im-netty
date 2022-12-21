package com.itrjp.im.api.service;

import com.itrjp.im.api.entity.ChannelNodeInfo;

import java.util.List;

public interface ConnectNodeService {
    /**
     * 获取最佳节点
     *
     * @param channelId
     * @return
     */
    List<ChannelNodeInfo> getBestNode(String channelId);
}
