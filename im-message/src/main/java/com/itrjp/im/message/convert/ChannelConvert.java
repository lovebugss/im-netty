package com.itrjp.im.message.convert;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.entity.Channels;
import com.itrjp.im.proto.ChannelProto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChannelConvert {
    ChannelConvert INST = Mappers.getMapper(ChannelConvert.class);

    @Mapping(target = "limit", source = "channelLimit", defaultValue = "-1")
    @Mapping(target = "channelId", source = "channelId")
    ChannelProto.ChannelInfo toChannelInfo(Channels channels);

}
