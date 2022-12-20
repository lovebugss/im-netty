package com.itrjp.im.message.entity;

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
    private MessageFilter.MessageFilterType filterType = MessageFilter.MessageFilterType.AUTO;

    public MessageFilter.MessageFilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(MessageFilter.MessageFilterType filterType) {
        this.filterType = filterType;
    }
}
