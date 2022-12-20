package com.itrjp.im.message.service.impl;

import com.itrjp.im.message.entity.ChannelConfig;
import com.itrjp.im.message.service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {


    @Override
    public ChannelConfig getChannelConfig(String channelId) {
        return new ChannelConfig();
    }
}
