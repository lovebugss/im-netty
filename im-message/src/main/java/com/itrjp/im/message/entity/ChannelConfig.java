package com.itrjp.im.message.entity;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.service.filter.MessageFilter;

public class ChannelConfig {

    private String channelId;
    /**
     * 最大用户数
     */
    private long maxUserCount;
    /**
     * 消息过滤器
     */
    private MessageFilterType filterType = MessageFilterType.AUTO;

    public MessageFilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(MessageFilterType filterType) {
        this.filterType = filterType;
    }
}
