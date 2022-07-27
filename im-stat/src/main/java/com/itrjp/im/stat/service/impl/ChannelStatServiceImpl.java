package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.pojo.ChannelInfo;
import com.itrjp.im.stat.service.ChannelStatService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:12
 */
@Service
public class ChannelStatServiceImpl implements ChannelStatService {
    private final RedisTemplate<String, String> redisTemplate;

    public ChannelStatServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void online(String channelId, String sessionId, String userName) {

        redisTemplate.opsForValue().increment("im:stat:channel:pv:" + channelId);
    }

    @Override
    public void offline(String channelId, String sessionId, String userName) {
        redisTemplate.opsForValue().decrement("im:stat:channel:pv:" + channelId);
    }


    @Override
    public ChannelInfo getChannelInfo(String channelId) {
        return new ChannelInfo();
    }
}
