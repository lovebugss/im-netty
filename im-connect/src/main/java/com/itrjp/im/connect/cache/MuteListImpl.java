package com.itrjp.im.connect.cache;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/3 00:37
 */
@Service
public class MuteListImpl implements MuteList {
    /**
     * 禁言用户
     */
    private final Map<String, Set<String>> muteUsers = new ConcurrentHashMap<>();

    @Override
    public boolean contains(String channelId, String uid) {
        return muteUsers.containsKey(channelId) &&
                muteUsers.get(channelId).contains(uid);
    }

    @Override
    public void mute(String channelId, String userId) {
        muteUsers.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet())
                .add(userId);

    }

    @Override
    public void unMute(String channelId, String userId) {
        if (muteUsers.containsKey(channelId)) {
            muteUsers.get(channelId).remove(userId);
        }
    }

    @Override
    public void sync(String channelId, Collection<String> users) {
        ConcurrentHashMap.KeySetView<String, Boolean> objects = ConcurrentHashMap.newKeySet();
        objects.addAll(users);
        muteUsers.put(channelId, objects);
    }

    @Override
    public void remove(String channelId) {
        muteUsers.remove(channelId);
    }
}
