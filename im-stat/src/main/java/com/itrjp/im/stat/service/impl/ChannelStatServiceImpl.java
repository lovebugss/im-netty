package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.pojo.ChannelInfo;
import com.itrjp.im.stat.service.ChannelStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.itrjp.common.consts.CacheKeyConstant.IM_STAT_CHANNEL_PV;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:12
 */
@Service
@RequiredArgsConstructor
public class ChannelStatServiceImpl implements ChannelStatService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void online(String channelId, String sessionId, String userName) {

        redisTemplate.opsForValue().increment(IM_STAT_CHANNEL_PV + channelId);
    }

    @Override
    public void offline(String channelId, String sessionId, String userName) {
        redisTemplate.opsForValue().decrement(IM_STAT_CHANNEL_PV + channelId);
    }


    @Override
    public ChannelInfo getChannelInfo(String channelId) {
        return new ChannelInfo();
    }

    @Override
    public Optional<Integer> getChannelConnectCount(String channelId) {
        String count = redisTemplate.opsForValue().get(IM_STAT_CHANNEL_PV + channelId);
        return Optional.of(count == null ? 0 : Integer.parseInt(count));
    }

    @Override
    public Optional<Integer> getChannelUserCount(String channelId) {
        return Optional.empty();
    }
}
