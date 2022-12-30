package com.itrjp.im.connect.service;

import com.itrjp.im.proto.Event;
import com.itrjp.im.proto.EventType;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 22:38
 */
public interface EventHandlerService {
    boolean support(EventType type);

    void handle(Event event);
}
