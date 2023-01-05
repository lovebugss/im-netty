package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.cache.MuteList;
import com.itrjp.im.connect.service.EventHandlerService;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.Event;
import com.itrjp.im.proto.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 禁言, 取消禁言
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 11:42
 */
@Service
@RequiredArgsConstructor
public class MuteUnMuteEventHandler implements EventHandlerService {
    private final MuteList muteList;
    private final MessageService messageService;

    @Override
    public boolean support(EventType type) {
        return EventType.MUTE.equals(type) || EventType.UN_MUTE.equals(type);
    }

    @Override
    public void handle(Event event) {
        EventType type = event.getType();
        String channelId = event.getChannelId();
        if (EventType.MUTE.equals(type)) {
            muteList.mute(channelId, event.getUserId());
        } else if (EventType.UN_MUTE.equals(type)) {
            muteList.unMute(event.getChannelId(), event.getUserId());
        }
        messageService.broadcastEvent(channelId, event);
    }
}


