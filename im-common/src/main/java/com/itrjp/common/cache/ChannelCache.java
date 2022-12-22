package com.itrjp.common.cache;

import com.itrjp.common.entity.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChannelCache extends Cache<Channel> {

    public ChannelCache(Store<Channel> channelStore) {
        super(channelStore);
    }
}
