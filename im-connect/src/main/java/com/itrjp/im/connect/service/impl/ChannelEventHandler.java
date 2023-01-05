package com.itrjp.im.connect.service.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.itrjp.im.connect.service.EventHandlerService;
import com.itrjp.im.connect.service.MessageHandler;
import com.itrjp.im.proto.DataType;
import com.itrjp.im.proto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事件处理器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 18:20
 * @see com.itrjp.im.proto.EventType
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelEventHandler implements MessageHandler {
    private final List<EventHandlerService> eventHandler;

    @Override
    public boolean support(DataType dataType) {
        return DataType.EVENT.equals(dataType);
    }

    @Override
    public void handle(String from, String to, long timestamp, ByteString data) {
        log.info("收到事件: from: {}, to: {}, timestamp: {}, data: {}", from, to, timestamp, data.size());
        try {
            Event event = Event.parseFrom(data);
            for (EventHandlerService handlerService : eventHandler) {
                if (handlerService.support(event.getType())) {
                    handlerService.handle(event);
                }
            }
        } catch (InvalidProtocolBufferException e) {
            log.warn("事件解析失败", e);
        }
    }
}
