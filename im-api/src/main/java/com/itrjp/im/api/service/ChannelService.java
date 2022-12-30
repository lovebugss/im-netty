package com.itrjp.im.api.service;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.pojo.param.CreateChannelParam;
import com.itrjp.im.proto.ChannelInfo;

import java.util.Optional;

public interface ChannelService {
    /**
     * 检查当前channel是否存在
     *
     * @param channelId
     * @throws ChannelNotFoundException
     */
    void checkChannelId(String channelId);

    Optional<ChannelInfo> getChannel(String channelId);

    Channel createChannel(CreateChannelParam param);
}
