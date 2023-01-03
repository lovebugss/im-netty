package com.itrjp.im.connect.cache;

import java.util.Collection;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/3 16:47
 */
public interface ChannelBlackList {
    boolean contains(String channelId, String userId);

    void disable(String channelId, String userId);

    void enable(String channelId, String userId);

    void sync(String channelId, Collection<String> users);

    void remove(String channelId);
}
