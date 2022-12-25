package com.itrjp.im.connect.listener;

import com.itrjp.im.connect.service.impl.MessageServiceImpl;
import com.itrjp.im.proto.Data;
import com.itrjp.im.proto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Kafka 监听器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:49
 */
@Component
public class KafkaMessageListener {
    private final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    private final MessageServiceImpl messageService;

    public KafkaMessageListener(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    /**
     * 消息
     *
     * @param data
     */
    @KafkaListener(topics = {"${im.connect-topic}"})
    public void onMessage(byte[] data) {
        try {
            Data d = Data.parseFrom(data);
            logger.info("接受Kafka消息: {}", d);
            messageService.broadcastMessage(d.getChannelId(), d);
        } catch (Exception e) {
            logger.error("消息处理失败", e);
        }
    }

    /**
     * 上下线消息
     *
     * @param data
     */
    @KafkaListener(topics = {"${im.connect-notify-topic}"})
    public void onJoinLeave(byte[] data) {
        try {
            Event event = Event.parseFrom(data);
            logger.info("接受Kafka上下线消息: {}, type: {}", event, event.getType());
            messageService.broadcastEvent(event.getChannelId(), event);
        } catch (Exception e) {
            logger.error("上下线消息处理失败", e);
        }
    }
}
