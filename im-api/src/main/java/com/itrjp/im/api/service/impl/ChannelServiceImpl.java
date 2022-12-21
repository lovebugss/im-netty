package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Override
    public void checkChannelId(String channelId) {
        Channel channel = getChannel(channelId);
        if (channel == null) {
            throw new ChannelNotFoundException();
        }
    }

    @Override
    public Channel getChannel(String channelId) {
        return null;
    }
}
