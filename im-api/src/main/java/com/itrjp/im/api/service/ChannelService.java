package com.itrjp.im.api.service;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.entity.Channel;

public interface ChannelService {
    /**
     * 检查当前channel是否存在
     *
     * @param channelId
     * @throws ChannelNotFoundException
     */
    void checkChannelId(String channelId);

    Channel getChannel(String channelId);
}