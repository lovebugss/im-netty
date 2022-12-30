package com.itrjp.im.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itrjp.im.message.entity.ImChannels;
import com.itrjp.im.proto.ChannelInfo;

import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author renjp
 * @since 2022-12-27
 */
public interface IImChannelsService extends IService<ImChannels> {
    Optional<ChannelInfo> getByChannelId(String channelId);
}
