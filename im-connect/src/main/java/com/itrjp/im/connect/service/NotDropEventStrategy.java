package com.itrjp.im.connect.service;

import com.itrjp.im.proto.message.Event;

/**
 * 不允许丢弃任何事件
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/30 08:34
 */
public class NotDropEventStrategy implements DropEventStrategy {
    @Override
    public boolean allowDrop(Event event) {
        return false;
    }
}
