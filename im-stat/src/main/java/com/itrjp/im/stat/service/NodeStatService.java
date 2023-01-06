package com.itrjp.im.stat.service;

import com.itrjp.im.proto.connect.ChannelNodeInfo;

import java.util.List;
import java.util.Optional;

/**
 * 服务器节点统计
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:00
 */
public interface NodeStatService {

    /**
     * 连接
     */
    void connected(String nodeId, String channelID, String userId, String sessionId);

    /**
     * 断开连接
     */
    void disConnected(String nodeId, String channelId, String userId, String sessionId);


    /**
     * 服务启动
     */
    void start(ChannelNodeInfo nodeInfo);

    /**
     * 服务停止
     */
    void stop(ChannelNodeInfo nodeInfo);

    List<ChannelNodeInfo> getAvailableList();

    List<ChannelNodeInfo> getBestNode(String channelId);

    Optional<List<ChannelNodeInfo>> getConnectNodeByChannelId(String channelId);
}
