package com.itrjp.im.connect.websocket.cache;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/27 21:58
 */
public interface CacheFactory {

    Cache createCache(String name);
}
