package com.itrjp.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore<T> implements Store<T> {
    private final Map<String, T> cache = new ConcurrentHashMap<>(32);

    @Override
    public T get(String key) {
        return cache.get(key);
    }

    @Override
    public T set(String key, T value) {
        return cache.put(key, value);
    }

    @Override
    public boolean remove(String key) {
        return cache.remove(key) != null;
    }

    @Override
    public boolean has(String key) {
        return cache.containsKey(key);
    }
}
