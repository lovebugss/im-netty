package com.itrjp.im.connect.listener;

import com.itrjp.im.connect.service.impl.MessageServiceImpl;
import com.itrjp.im.proto.dto.MessageProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.itrjp.common.consts.KafkaConstant.MESSAGE_JOIN_LEAVE_TOPIC;
import static com.itrjp.common.consts.KafkaConstant.MESSAGE_TOPIC;

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
    @KafkaListener(topics = {MESSAGE_TOPIC})
    public void onMessage(byte[] data) {
        try {
            MessageProto.Message d = MessageProto.Message.parseFrom(data);
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
    @KafkaListener(topics = {MESSAGE_JOIN_LEAVE_TOPIC})
    public void onJoinLeave(byte[] data) {
        try {
            MessageProto.Event event = MessageProto.Event.parseFrom(data);
            logger.info("接受Kafka上下线消息: {}, type: {}", event, event.getType());
            messageService.broadcastEvent(event.getChannelId(), event);
        } catch (Exception e) {
            logger.error("上下线消息处理失败", e);
        }
    }
}
