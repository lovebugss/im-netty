package com.itrjp.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/20 00:11
 */
@Service
public class DefaultTokenService implements TokenService {

    @Value("${im.common.sign:123456}")
    private String sign;

    @Override
    public boolean check(String token) {
        return false;
    }

    @Override
    public String create(String channelId) {
        // token 格式:
        // channelId:
        return null;
    }
}
