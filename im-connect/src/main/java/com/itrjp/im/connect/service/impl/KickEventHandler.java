package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.EventHandlerService;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.proto.message.Event;
import com.itrjp.im.proto.message.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 踢出用户
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 12:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KickEventHandler implements EventHandlerService {
    private final MessageService messageService;
    private final ChannelsHub channelsHub;

    @Override
    public boolean support(EventType type) {
        return EventType.KICK.equals(type);
    }

    @Override
    public void handle(Event event) {
        log.info("kick event, channelId: {}, userId: {}", event.getChannelId(), event.getUserId());
        String channelId = event.getChannelId();
        String userId = event.getUserId();
        for (WebSocketClient webSocketClient : channelsHub.getUserClient(channelId, userId)) {
            if (log.isDebugEnabled()) {
                log.debug("close channel, channelId: {}, userId:{}, sessionId: {}", channelId, userId, webSocketClient.getSession());
            }
            webSocketClient.close();
        }
    }

}





