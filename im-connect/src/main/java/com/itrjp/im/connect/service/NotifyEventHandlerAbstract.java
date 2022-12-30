package com.itrjp.im.connect.service;

import com.itrjp.im.connect.service.DropEventStrategy;
import com.itrjp.im.connect.service.EventHandlerService;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.Event;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下线消息处理器
 * <p>
 * 上下线允许丢失
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 22:44
 */
@Slf4j
public abstract class NotifyEventHandlerAbstract implements EventHandlerService {
    private final MessageService messageService;
    private final DropEventStrategy dropEventStrategy;


    protected NotifyEventHandlerAbstract(MessageService messageService, DropEventStrategy dropEventStrategy) {
        this.messageService = messageService;
        this.dropEventStrategy = dropEventStrategy;
    }

    @Override
    public void handle(Event event) {
        boolean allowDrop = dropEventStrategy.allowDrop(event);
        if (allowDrop) {
            log.warn("丢弃事件:{}", event);
            return;
        }
        messageService.broadcastEvent(event.getChannelId(), event);
    }
}
