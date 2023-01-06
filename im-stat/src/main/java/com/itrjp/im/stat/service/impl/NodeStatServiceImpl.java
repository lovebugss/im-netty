package com.itrjp.im.stat.service.impl;

import com.itrjp.common.util.ProtobufUtils;
import com.itrjp.im.proto.connect.ChannelNodeInfo;
import com.itrjp.im.stat.service.NodeStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.itrjp.common.consts.CacheKeyConstant.*;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NodeStatServiceImpl implements NodeStatService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void connected(String nodeId, String channelID, String userId, String sessionId) {
        redisTemplate.opsForValue().increment(IM_CONNECT_NODE_LOAD + nodeId);
        redisTemplate.opsForZSet().incrementScore(CONNECT_NODE_LOAD, nodeId, 1);
        redisTemplate.opsForSet().add(IM_CHANNEL_CONNECT_NODE_MAPPING + channelID, nodeId);
    }

    @Override
    public void disConnected(String nodeId, String channelId, String userId, String sessionId) {
        Long decrement = redisTemplate.opsForValue().decrement(IM_CONNECT_NODE_LOAD + nodeId);
        if (decrement == null || decrement <= 0) {
            redisTemplate.delete(IM_CONNECT_NODE_LOAD + nodeId);
        }
        redisTemplate.opsForZSet().incrementScore(CONNECT_NODE_LOAD, nodeId, -1);
        redisTemplate.opsForSet().remove(IM_CHANNEL_CONNECT_NODE_MAPPING + channelId, nodeId);

    }

    @Override
    public void start(ChannelNodeInfo nodeInfo) {
        // 重置
        redisTemplate.opsForValue().set(IM_CONNECT_NODE_LOAD + nodeInfo.getNodeId(), "0");
        redisTemplate.opsForZSet().add(CONNECT_NODE_LOAD, nodeInfo.getNodeId(), 0);
        String json = ProtobufUtils.toJson(nodeInfo);
        if (json != null) {
            redisTemplate.opsForSet().add(CONNECT_NODE_LIST, json);
        }
    }

    @Override
    public void stop(ChannelNodeInfo nodeInfo) {
        redisTemplate.delete(IM_CONNECT_NODE_LOAD + nodeInfo.getNodeId());
        redisTemplate.opsForZSet().remove(CONNECT_NODE_LOAD, nodeInfo.getNodeId());
        String json = ProtobufUtils.toJson(nodeInfo);
        if (json != null) {
            redisTemplate.opsForSet().remove(CONNECT_NODE_LIST, json);
        }
    }

    @Override
    public List<ChannelNodeInfo> getAvailableList() {
        Set<String> range = redisTemplate.opsForSet().members(CONNECT_NODE_LIST);
        if (range == null) {
            return Collections.emptyList();
        }
        return range
                .stream()
                .map(json -> (ChannelNodeInfo) ProtobufUtils.fromJson(json, ChannelNodeInfo.newBuilder()))
                .filter(Objects::nonNull)
                .toList();

    }

    @Override
    public List<ChannelNodeInfo> getBestNode(String channelId) {
        Set<String> range = redisTemplate.opsForZSet().range(CONNECT_NODE_LOAD, 0, 1);
        if (range == null) {
            return Collections.emptyList();
        }
        Map<String, ChannelNodeInfo> map = getAvailableList()
                .stream()
                .collect(Collectors.toMap(ChannelNodeInfo::getNodeId, Function.identity()));
        return range.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Optional<List<ChannelNodeInfo>> getConnectNodeByChannelId(String channelId) {
        Set<String> members = redisTemplate.opsForSet().members(IM_CHANNEL_CONNECT_NODE_MAPPING + channelId);
        if (members == null) {
            return Optional.empty();
        }
        Map<String, ChannelNodeInfo> map = getAvailableList()
                .stream()
                .collect(Collectors.toMap(ChannelNodeInfo::getNodeId, Function.identity()));
        return Optional.of(members.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList());
    }
}
