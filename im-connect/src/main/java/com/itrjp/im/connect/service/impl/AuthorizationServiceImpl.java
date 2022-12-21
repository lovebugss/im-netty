package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.AuthorizationService;
import com.itrjp.im.connect.websocket.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public boolean authorize(HandshakeData data) {
        log.info("AuthorizationServiceImpl#authorize, data: {}", data);
        return true;
    }
}
