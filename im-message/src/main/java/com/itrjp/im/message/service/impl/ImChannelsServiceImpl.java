package com.itrjp.im.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itrjp.im.message.convert.ChannelConvert;
import com.itrjp.im.message.entity.ImChannels;
import com.itrjp.im.message.mapper.ImChannelsMapper;
import com.itrjp.im.message.service.IImChannelsService;
import com.itrjp.im.proto.ChannelInfo;
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
public class ImChannelsServiceImpl extends ServiceImpl<ImChannelsMapper, ImChannels> implements IImChannelsService {
    @Override
    public Optional<ChannelInfo> getByChannelId(String channelId) {
        ImChannels channels = this.baseMapper.selectOne(new LambdaQueryWrapper<ImChannels>()
                .eq(ImChannels::getChannelId, channelId));
        return Optional.ofNullable(ChannelConvert.INST.toChannelInfo(channels));
    }
}
