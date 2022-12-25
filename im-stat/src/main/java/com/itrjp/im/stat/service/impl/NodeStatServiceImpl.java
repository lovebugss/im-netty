package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.service.NodeStatService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.itrjp.common.consts.CacheKeyConstant.CONNECT_NODE;
import static com.itrjp.common.consts.CacheKeyConstant.CONNECT_NODE_LOAD;

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
        redisTemplate.opsForValue().increment(CONNECT_NODE + nodeName);
        redisTemplate.opsForZSet().incrementScore(CONNECT_NODE_LOAD, nodeName, 1);
    }

    @Override
    public void disConnected(String nodeName, String sessionId) {
        redisTemplate.opsForValue().decrement(CONNECT_NODE + nodeName);
        redisTemplate.opsForZSet().incrementScore(CONNECT_NODE_LOAD, nodeName, -1);

    }

    @Override
    public void start(String id) {
        // 重置
        redisTemplate.opsForValue().set(CONNECT_NODE + id, "0");
        redisTemplate.opsForZSet().add(CONNECT_NODE_LOAD, id, 0);
    }

    @Override
    public void stop(String id) {
        redisTemplate.delete(CONNECT_NODE + id);
        redisTemplate.opsForZSet().remove(CONNECT_NODE_LOAD, id);
    }
}
