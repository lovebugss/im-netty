package com.itrjp.im.api.service;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.pojo.param.CreateChannelParam;
import com.itrjp.im.proto.message.ChannelInfo;

import java.util.Optional;

public interface ChannelService {
    /**
     * 检查当前channel是否存在
     *
     * @param channelId 频道id
     * @throws ChannelNotFoundException
     */
    void checkChannelId(String channelId);

    Optional<ChannelInfo> getChannel(String channelId);

    Channel createChannel(CreateChannelParam param);

    /**
     * 检查当前用户是否在频道中
     *
     * @param channelId 频道id
     * @param userId    用户id
     */
    void checkUserInChannel(String channelId, String userId);

    /**
     * 用户禁言
     *
     * @param channelId 频道id
     * @param userId    用户id
     */
    void muteUser(String channelId, String userId);

    /**
     * 解除禁言
     *
     * @param channelId 频道id
     * @param userId    用户id
     */
    void unMuteUser(String channelId, String userId);
}
