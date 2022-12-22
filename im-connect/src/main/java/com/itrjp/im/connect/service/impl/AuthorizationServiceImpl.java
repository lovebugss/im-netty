package com.itrjp.im.connect.service.impl;

import com.itrjp.common.service.TokenService;
import com.itrjp.im.connect.service.AuthorizationService;
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

    @Override
    public boolean authorize(HandshakeData data) {
        log.info("AuthorizationServiceImpl#authorize, data: {}", data);
        Map<String, List<String>> parameters = data.parameters();
        if (parameters == null || parameters.isEmpty()) {
            return false;
        }
        List<String> token = parameters.getOrDefault("t", new ArrayList<>());
        List<String> channelId = parameters.getOrDefault("cid", new ArrayList<>());
        List<String> uid = parameters.getOrDefault("uid", new ArrayList<>());
        List<String> time = parameters.getOrDefault("tm", new ArrayList<>());
        if (time.isEmpty()) {
            return false;
        }
        return tokenService.check(token.get(0), channelId.get(0), Long.parseLong(time.get(0)), uid.get(0));
    }
}
