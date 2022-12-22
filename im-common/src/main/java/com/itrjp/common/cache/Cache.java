package com.itrjp.common.cache;

/**
 * 缓存
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:09
 */
public abstract class Cache<T> {
    private final Store<T> store;

    protected Cache(Store<T> store) {
        this.store = store;
    }

    public T get(String key) {
        return store.get(key);
    }

    public T set(String key, T value) {
        return store.set(key, value);
    }

    public boolean remove(String key) {
        return store.remove(key);
    }

    public boolean has(String key) {
        return store.has(key);
    }
}
