package com.itrjp.im.connect.service.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.itrjp.im.connect.service.MessageHandler;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.DataType;
import com.itrjp.im.proto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 事件处理器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 18:20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelEventHandler implements MessageHandler {
    private final MessageService messageService;

    @Override
    public boolean support(DataType dataType) {
        return DataType.EVENT.equals(dataType);
    }

    @Override
    public void handle(String from, String to, long timestamp, ByteString data) {
        log.info("收到事件: from: {}, to: {}, timestamp: {}, data: {}", from, to, timestamp, data.size());
        try {
            Event event = Event.parseFrom(data);
            messageService.broadcastEvent(to, event);
        } catch (InvalidProtocolBufferException e) {
            log.warn("事件解析失败", e);
        }
    }
}
