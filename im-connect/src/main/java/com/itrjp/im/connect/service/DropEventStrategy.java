package com.itrjp.im.connect.service;

import com.itrjp.im.proto.message.Event;

/**
 * 丢弃事件策略
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 23:01
 */
public interface DropEventStrategy {
    enum DropType {
        /**
         * 默认丢弃策略
         */
        DEFAULT,
        /**
         * 不丢弃任何事件
         */
        NOT_DROP,
        /**
         * 丢弃所有事件
         */
        DROP_ALL;

    }

    /**
     * 是否允许丢弃事件
     *
     * @param event
     * @return
     */
    boolean allowDrop(Event event);
}
