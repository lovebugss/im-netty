package com.itrjp.im.connect.cache;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/3 17:07
 */
@Service
public class ChannelBlackListImpl implements ChannelBlackList {
    private final Map<String, Set<String>> blackList = new ConcurrentHashMap<>();

    @Override
    public boolean contains(String channelId, String userId) {
        return blackList.containsKey(channelId) &&
                blackList.get(channelId).contains(userId);
    }

    @Override
    public void disable(String channelId, String userId) {
        blackList.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet()).add(userId);
    }

    @Override
    public void enable(String channelId, String userId) {
        if (blackList.containsKey(channelId)) {
            blackList.get(channelId).remove(userId);
        }
    }

    @Override
    public void sync(String channelId, Collection<String> users) {
        ConcurrentHashMap.KeySetView<String, Boolean> objects = ConcurrentHashMap.newKeySet();
        objects.addAll(users);
        blackList.put(channelId, objects);
    }

    @Override
    public void remove(String channelId) {
        blackList.remove(channelId);
    }
}
