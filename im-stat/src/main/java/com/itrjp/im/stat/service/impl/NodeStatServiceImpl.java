package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.service.NodeStatService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:03
 */
@Service
public class NodeStatServiceImpl implements NodeStatService {
    private final RedisTemplate<String, String> redisTemplate;

    public NodeStatServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void connected(String nodeName, String sessionId) {
        redisTemplate.opsForValue().increment("im:stat:node:" + nodeName);
    }

    @Override
    public void disConnected(String nodeName, String sessionId) {
        redisTemplate.opsForValue().decrement("im:stat:node:" + nodeName);
    }

    @Override
    public void start(String nodeName) {
        // 重置
        redisTemplate.delete("im:stat:node:" + nodeName);
    }

    @Override
    public void stop(String nodeName) {

    }
}
