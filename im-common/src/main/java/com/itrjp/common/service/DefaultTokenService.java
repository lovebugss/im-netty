package com.itrjp.common.service;

import com.itrjp.common.util.Md5Utils;
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
    /**
     * 超时时间(s)
     */
    @Value("${im.token.expire:300}")
    private long expire = 5 * 60L;

    @Override
    public boolean check(String token, String channelId, long time, String uid) {

        return token.equals(create(channelId, time, uid)) && checkTime(time);
    }

    @Override
    public boolean checkTime(long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        return currentTime < time + expire && currentTime > time - 60;
    }

    @Override
    public String create(String channelId, long currentTime, String uid) {
        // token 格式:
        // channelId:
        return Md5Utils.create(channelId + uid + channelId + currentTime + sign);
    }
}
