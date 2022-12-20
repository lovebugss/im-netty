package com.itrjp.im.message.service;

import com.itrjp.im.message.entity.ChannelConfig;

public interface ChannelService {

    /**
     * 根据channelId获取channel配置
     *
     * @param channelId
     * @return
     */
    ChannelConfig getChannelConfig(String channelId);
}
