package com.itrjp.im.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.message.mapper.ChannelsMapper;
import com.itrjp.im.message.service.IChannelsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author renjp
 * @since 2022-12-21
 */
@Service
public class ChannelsServiceImpl extends ServiceImpl<ChannelsMapper, Channels> implements IChannelsService {

    @Override
    public Channels getByChannelId(String channelId) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<Channels>().eq(Channels::getChannelId, channelId));
    }
}
