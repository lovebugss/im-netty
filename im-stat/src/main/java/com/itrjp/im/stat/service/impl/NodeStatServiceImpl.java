package com.itrjp.im.stat.service.impl;

import com.itrjp.common.util.ProtobufUtils;
import com.itrjp.im.proto.connect.ChannelNodeInfo;
import com.itrjp.im.stat.service.NodeStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            connection.incrBy((IM_CONNECT_NODE_LOAD + nodeId).getBytes(StandardCharsets.UTF_8), 1);
            connection.zSetCommands().zIncrBy((CONNECT_NODE_LOAD).getBytes(StandardCharsets.UTF_8), 1, nodeId.getBytes(StandardCharsets.UTF_8));
            connection.sAdd((IM_CHANNEL_CONNECT_NODE_MAPPING + channelID).getBytes(StandardCharsets.UTF_8), nodeId.getBytes(StandardCharsets.UTF_8));
            return null;
        });
    }

    @Override
    public void disConnected(String nodeId, String channelId, String userId, String sessionId) {

        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            Long decr = connection.decr((IM_CONNECT_NODE_LOAD + nodeId).getBytes(StandardCharsets.UTF_8));
            if (decr == null || decr <= 0) {
                connection.del((IM_CONNECT_NODE_LOAD + nodeId).getBytes(StandardCharsets.UTF_8));
            }
            connection.zSetCommands().zIncrBy((CONNECT_NODE_LOAD).getBytes(StandardCharsets.UTF_8), -1, nodeId.getBytes(StandardCharsets.UTF_8));
            connection.sRem((IM_CHANNEL_CONNECT_NODE_MAPPING + channelId).getBytes(StandardCharsets.UTF_8), nodeId.getBytes(StandardCharsets.UTF_8));
            return null;
        });
    }

    @Override
    public void start(ChannelNodeInfo nodeInfo) {
        // 重置

        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            connection.del((IM_CONNECT_NODE_LOAD + nodeInfo.getNodeId()).getBytes(StandardCharsets.UTF_8));
            connection.zSetCommands().zAdd((CONNECT_NODE_LOAD).getBytes(StandardCharsets.UTF_8), 0, nodeInfo.getNodeId().getBytes(StandardCharsets.UTF_8));
            String json = ProtobufUtils.toJson(nodeInfo);
            if (json != null) {
                connection.hSet((CONNECT_NODE_LIST).getBytes(StandardCharsets.UTF_8), nodeInfo.getNodeId().getBytes(StandardCharsets.UTF_8), json.getBytes(StandardCharsets.UTF_8));
            }
            return null;
        });
    }

    @Override
    public void stop(ChannelNodeInfo nodeInfo) {
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            connection.del((IM_CONNECT_NODE_LOAD + nodeInfo.getNodeId()).getBytes(StandardCharsets.UTF_8));
            connection.zSetCommands().zRem((CONNECT_NODE_LOAD).getBytes(StandardCharsets.UTF_8), nodeInfo.getNodeId().getBytes(StandardCharsets.UTF_8));
            connection.hDel((CONNECT_NODE_LIST).getBytes(StandardCharsets.UTF_8), nodeInfo.getNodeId().getBytes(StandardCharsets.UTF_8));
            return null;
        });
    }

    @Override
    public List<ChannelNodeInfo> getAvailableList() {
        List<Object> range = redisTemplate.opsForHash().values(CONNECT_NODE_LIST);
        if (range.isEmpty()) {
            return Collections.emptyList();
        }
        return range
                .stream()
                .map(json -> (ChannelNodeInfo) ProtobufUtils.fromJson(json.toString(), ChannelNodeInfo.newBuilder()))
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
