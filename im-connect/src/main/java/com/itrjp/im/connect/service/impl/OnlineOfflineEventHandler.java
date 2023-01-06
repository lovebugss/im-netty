package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.AbstractNotifyEventHandler;
import com.itrjp.im.connect.service.DropEventStrategy;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.message.EventType;
import org.springframework.stereotype.Service;

/**
 * 上下线
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 11:40
 */
@Service
public class OnlineOfflineEventHandler extends AbstractNotifyEventHandler {
    public OnlineOfflineEventHandler(MessageService messageService, DropEventStrategy dropEventStrategy) {
        super(messageService, dropEventStrategy);
    }

    @Override
    public boolean support(EventType type) {
        return EventType.ONLINE.equals(type) || EventType.OFFLINE.equals(type);
    }
}
