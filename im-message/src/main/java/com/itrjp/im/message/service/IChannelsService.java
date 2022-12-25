package com.itrjp.im.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.proto.ChannelProto;

import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author renjp
 * @since 2022-12-21
 */
public interface IChannelsService extends IService<Channels> {

    Optional<ChannelProto.ChannelInfo> getByChannelId(String channelId);
}
