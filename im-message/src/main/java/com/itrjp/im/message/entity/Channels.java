package com.itrjp.im.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itrjp.common.enums.MessageFilterType;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author renjp
 * @since 2022-12-21
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("IM_CHANNELS")
public class Channels implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("CHANNEL_ID")
    private String channelId;

    @TableField("FILTER_TYPE")
    private MessageFilterType filterType;

    @TableField("CHANNEL_LIMIT")
    private Integer channelLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public MessageFilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(MessageFilterType filterType) {
        this.filterType = filterType;
    }

    public Integer getChannelLimit() {
        return channelLimit;
    }

    public void setChannelLimit(Integer channelLimit) {
        this.channelLimit = channelLimit;
    }
}
