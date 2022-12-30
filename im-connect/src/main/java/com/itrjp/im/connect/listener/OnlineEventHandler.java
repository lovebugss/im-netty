package com.itrjp.im.connect.listener;

import com.itrjp.im.connect.service.DropEventStrategy;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.service.NotifyEventHandlerAbstract;
import com.itrjp.im.proto.EventType;
import org.springframework.stereotype.Service;

/**
 * 上线
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 22:41
 */
@Service
public class OnlineEventHandler extends NotifyEventHandlerAbstract {
    protected OnlineEventHandler(MessageService messageService, DropEventStrategy dropEventStrategy) {
        super(messageService, dropEventStrategy);
    }

    @Override
    public boolean support(EventType type) {
        return EventType.ONLINE.equals(type);
    }
}
