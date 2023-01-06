package com.itrjp.im.connect.service.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.itrjp.im.connect.service.MessageHandler;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.message.DataType;
import com.itrjp.im.proto.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO消息处理器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 18:18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelMessageHandler implements MessageHandler {
    private final MessageService messageService;

    @Override
    public boolean support(DataType dataType) {
        return DataType.MSG.equals(dataType);
    }

    @Override
    public void handle(String from, String to, long timestamp, ByteString data) {
        log.info("收到消息: from: {}, to: {}, timestamp: {}, data: {}", from, to, timestamp, data.size());
        try {
            Message message = Message.parseFrom(data);
            messageService.broadcastMessage(to, message);
        } catch (InvalidProtocolBufferException e) {
            log.warn("消息解析失败", e);
        }

    }
}
