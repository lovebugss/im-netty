package com.itrjp.common.entity;

import com.itrjp.common.enums.MessageFilterType;
import lombok.Data;

@Data
public class Channel {
    private String channelId;
    private int limit;
    private MessageFilterType filterType;
}
