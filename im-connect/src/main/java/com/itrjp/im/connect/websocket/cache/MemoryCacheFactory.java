package com.itrjp.im.connect.websocket.cache;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/27 22:04
 */
public class MemoryCacheFactory implements CacheFactory {

    @Override
    public Cache createCache(String name) {
        return new MemoryCache();
    }
}
