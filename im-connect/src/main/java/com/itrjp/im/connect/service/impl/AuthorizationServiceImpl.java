package com.itrjp.im.connect.service.impl;

import com.itrjp.common.cache.ChannelCache;
import com.itrjp.common.entity.Channel;
import com.itrjp.common.service.TokenService;
import com.itrjp.im.connect.cache.ChannelBlackList;
import com.itrjp.im.connect.properties.ConnectProperties;
import com.itrjp.im.connect.service.AuthorizationService;
import com.itrjp.im.connect.service.ChannelStateService;
import com.itrjp.im.connect.websocket.HandshakeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final TokenService tokenService;
    private final ChannelCache channelCache;
    private final ChannelStateService channelStateService;
    private final ChannelBlackList channelBlackList;
    private final ConnectProperties connectProperties;

    @Override
    public AuthorizationResult authorize(HandshakeData data) {
        log.info("AuthorizationServiceImpl#authorize, data: {}", data);
        Map<String, List<String>> parameters = data.parameters();
        if (parameters == null || parameters.isEmpty()) {
            return AuthorizationResult.fail(ErrorType.TOKEN_INVALID);
        }
        List<String> token = parameters.getOrDefault("t", new ArrayList<>());
        List<String> channelId = parameters.getOrDefault("cid", new ArrayList<>());
        List<String> uid = parameters.getOrDefault("uid", new ArrayList<>());
        List<String> time = parameters.getOrDefault("tm", new ArrayList<>());
        if (time.isEmpty()) {
            return AuthorizationResult.fail(ErrorType.TOKEN_INVALID);
        }
        if (!tokenService.check(token.get(0), channelId.get(0), Long.parseLong(time.get(0)), uid.get(0))) {
            return AuthorizationResult.fail(ErrorType.TOKEN_INVALID);
        }
        // 检查token 是否过期
        if (!tokenService.checkTime(Long.parseLong(time.get(0)))) {
            return AuthorizationResult.fail(ErrorType.TOKEN_EXPIRES);
        }
        if (channelBlackList.contains(channelId.get(0), uid.get(0))) {
            return AuthorizationResult.fail(ErrorType.IN_BLACK_LIST);
        }

        // 检查频道是否被限流
        if (checkChannelThrottling(channelId.get(0))) {
            return AuthorizationResult.fail(ErrorType.ROOM_THROTTLING);
        }

        return AuthorizationResult.success();
    }

    private boolean checkChannelThrottling(String channelId) {
        // 获取当前频道的连接数
        int count = channelStateService.getChannelConnectCount(channelId);
        // 获取频道的限流数
        Channel channel = channelCache.get(channelId);
        return count >= (channel == null ? connectProperties.getChannelLimit() : channel.getLimit());
    }
}
