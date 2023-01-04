package com.itrjp.im.connect.listener;

import com.itrjp.im.connect.service.CommandHandler;
import com.itrjp.im.connect.service.MessageHandler;
import com.itrjp.im.proto.Command;
import com.itrjp.im.proto.DataType;
import com.itrjp.im.proto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.itrjp.common.consts.KafkaConstant.CONNECT_COMMAND_TOPIC;


/**
 * Kafka 监听器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:49
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final List<MessageHandler> messageHandler;
    private final List<CommandHandler> commandHandlers;

    /**
     * 消息
     *
     * @param data
     */
    @KafkaListener(topics = {"${im.connect-topic}"})
    public void onMessage(byte[] data) {
        try {
            KafkaMessage kafkaMessage = KafkaMessage.parseFrom(data);
            DataType dataType = kafkaMessage.getDataType();
            for (MessageHandler handler : messageHandler) {
                if (handler.support(dataType)) {
                    handler.handle(kafkaMessage.getFrom(), kafkaMessage.getTo(), kafkaMessage.getTimestamp(), kafkaMessage.getData());
                }
            }
        } catch (Exception e) {
            log.error("消息处理失败", e);
        }
    }

    @KafkaListener(topics = {CONNECT_COMMAND_TOPIC})
    public void onCommand(byte[] data) {
        try {
            Command command = Command.parseFrom(data);
            for (CommandHandler commandHandler : commandHandlers) {
                if (commandHandler.support(command.getCommandType())) {
                    commandHandler.handle(command.getData());
                }
            }
        } catch (Exception e) {
            log.error("消息处理失败", e);
        }
    }
}
