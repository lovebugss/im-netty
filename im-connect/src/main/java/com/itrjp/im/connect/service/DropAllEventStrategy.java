package com.itrjp.im.connect.service;

import com.itrjp.im.proto.message.Event;

/**
 * 丢弃所有事件策略
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/30 08:34
 */
public class DropAllEventStrategy implements DropEventStrategy {
    @Override
    public boolean allowDrop(Event event) {
        return true;
    }
}
