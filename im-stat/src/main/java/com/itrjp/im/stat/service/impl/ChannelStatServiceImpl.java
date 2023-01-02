package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.pojo.ChannelInfo;
import com.itrjp.im.stat.service.ChannelStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.itrjp.common.consts.CacheKeyConstant.IM_CHANNEL_ONLINE_USER;
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
    public void online(String channelId, String userName, String sessionId) {
        // 统计pv
        redisTemplate.opsForValue().increment(IM_STAT_CHANNEL_PV + channelId);
        redisTemplate.opsForSet().add(IM_CHANNEL_ONLINE_USER + channelId, userName);
    }

    @Override
    public void offline(String channelId, String userName, String sessionId) {
        Long decrement = redisTemplate.opsForValue().decrement(IM_STAT_CHANNEL_PV + channelId);
        if (decrement == null || decrement <= 0) {
            redisTemplate.delete(IM_STAT_CHANNEL_PV + channelId);
        }
        redisTemplate.opsForSet().remove(IM_CHANNEL_ONLINE_USER + channelId, userName);
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
