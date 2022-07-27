package com.itrjp.im.storage.listener;

import com.itrjp.im.proto.kafka.KafkaProto;
import com.itrjp.im.storage.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.itrjp.common.consts.KafkaConstant.MESSAGE_JOIN_LEAVE_TOPIC;
import static com.itrjp.common.consts.KafkaConstant.MESSAGE_TOPIC;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 10:36
 */
@Component
public class KafkaMessageListener {
    private final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);
    private final MessageService messageService;

    public KafkaMessageListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = {MESSAGE_TOPIC})
    public void onMessage(byte[] data) {
        try {
            KafkaProto.Message d = KafkaProto.Message.parseFrom(data);
            logger.info("接受Kafka消息: {}", d);
            messageService.handlerMessage(d.getChannelId(), d.getContent());
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
            KafkaProto.JoinLeave d = KafkaProto.JoinLeave.parseFrom(data);
            logger.info("接受Kafka上下线消息: {}", d);
            messageService.handlerJoinLeaveMessage(d.getChannelId(), d.getUserId(), d.getType());
        } catch (Exception e) {
            logger.error("上下线消息处理失败", e);
        }
    }
}
