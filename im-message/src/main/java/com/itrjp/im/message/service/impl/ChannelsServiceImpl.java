package com.itrjp.im.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itrjp.im.message.convert.ChannelConvert;
import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.message.mapper.ChannelsMapper;
import com.itrjp.im.message.service.IChannelsService;
import com.itrjp.im.proto.message.ChannelInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author renjp
 * @since 2022-12-27
 */
@Service
public class ChannelsServiceImpl extends ServiceImpl<ChannelsMapper, Channels> implements IChannelsService {
    @Override
    public Optional<ChannelInfo> getByChannelId(String channelId) {
        Channels channels = this.baseMapper.selectOne(new LambdaQueryWrapper<Channels>()
                .eq(Channels::getChannelId, channelId));
        return Optional.ofNullable(ChannelConvert.INST.toChannelInfo(channels));
    }
}
