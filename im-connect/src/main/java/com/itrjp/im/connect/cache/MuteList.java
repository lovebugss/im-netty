package com.itrjp.im.connect.cache;

import java.util.Collection;

/**
 * 禁言列表
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/3 00:35
 */
public interface MuteList {
    // 检查用户是否被禁言
    boolean contains(String channelId, String userId);

    // 禁言用户
    void mute(String channelId, String userId);

    // 解除禁言
    void unMute(String channelId, String userId);

    void sync(String channelId, Collection<String> users);

    void remove(String channelId);
}
