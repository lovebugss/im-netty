package com.itrjp.common.cache;

public interface Store<T> {

    /**
     * 获取
     *
     * @param key
     * @return
     */
    T get(String key);


    /**
     * 设置
     *
     * @param key
     * @param value
     * @return
     */
    T set(String key, T value);

    /**
     * 删除
     *
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    boolean has(String key);
}
