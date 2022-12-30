package com.itrjp.im.connect.websocket.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的缓存
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/27 22:01
 */
public class MemoryCache implements Cache {
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Override
    public <T> T get(String key) {
        return (T) cache.get(key);
    }

    @Override
    public void set(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public boolean hasKey(String key) {
        return cache.containsKey(key);
    }
}
