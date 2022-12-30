package com.itrjp.im.connect.websocket.cache;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/27 21:56
 */
public interface Cache {
    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    <T> T get(String key);

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 删除缓存
     *
     * @param key
     */
    void remove(String key);

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);
}
