package com.itrjp.im.stat.service;

import com.itrjp.im.stat.pojo.ChannelInfo;

import java.util.Optional;

/**
 * 频道统计
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:05
 */
public interface ChannelStatService {
    /**
     * 上线
     */
    void online(String channelId, String userName, String sessionId);

    /**
     * 下线
     *
     * @param channelId
     * @param sessionId
     */
    void offline(String channelId, String userName, String sessionId);

    /**
     * 获取当前频道信息
     *
     * @param channelId
     * @return
     */
    ChannelInfo getChannelInfo(String channelId);

    Optional<Integer> getChannelConnectCount(String channelId);

    Optional<Integer> getChannelUserCount(String channelId);
}
