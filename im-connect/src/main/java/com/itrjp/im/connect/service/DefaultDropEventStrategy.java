package com.itrjp.im.connect.service;

import com.itrjp.im.proto.message.Event;

/**
 * 默认丢弃事件策略 延迟超过1秒, 自动丢弃, 不进行事件投递
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 23:06
 */
public class DefaultDropEventStrategy implements DropEventStrategy {
    private final long delay;

    public DefaultDropEventStrategy(long delay) {
        this.delay = delay;
    }

    @Override
    public boolean allowDrop(Event event) {
        long timestamp = event.getTimestamp();
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - timestamp > delay;
    }
}
