package com.itrjp.im.message.convert;

import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.proto.message.ChannelInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelConvert {
    ChannelConvert INST = Mappers.getMapper(ChannelConvert.class);

    @Mapping(target = "limit", source = "channelLimit", defaultValue = "-1")
    @Mapping(target = "channelId", source = "channelId")
    ChannelInfo toChannelInfo(Channels channels);

}
