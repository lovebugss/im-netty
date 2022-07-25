package com.itrjp.im.stat.service.impl;

import com.itrjp.im.stat.pojo.ChannelInfo;
import com.itrjp.im.stat.service.ChannelStatService;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:12
 */
public class ChannelStatServiceImpl implements ChannelStatService {
    @Override
    public void online(String channelId, String sessionId, String userName) {

    }

    @Override
    public void offline(String channelId, String sessionId, String userName) {

    }



    @Override
    public ChannelInfo getChannelInfo(String channelId) {
        return new ChannelInfo();
    }
}
