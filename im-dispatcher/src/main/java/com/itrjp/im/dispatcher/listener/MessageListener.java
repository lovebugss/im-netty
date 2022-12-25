package com.itrjp.im.dispatcher.listener;

import com.itrjp.im.proto.Data;
import com.itrjp.im.proto.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.itrjp.common.consts.KafkaConstant.MESSAGE_JOIN_LEAVE_TOPIC;
import static com.itrjp.common.consts.KafkaConstant.MESSAGE_TOPIC;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 21:37
 */
@Slf4j
@Component
public class MessageListener {

    /**
     * 消息
     *
     * @param data
     */
    @KafkaListener(topics = {MESSAGE_TOPIC})
    public void onMessage(byte[] data) {
        try {
            Data d = Data.parseFrom(data);
            log.info("接受Kafka消息: {}", d);
        } catch (Exception e) {
            log.error("消息处理失败", e);
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
            Event event = Event.parseFrom(data);
            log.info("接受Kafka上下线消息: {}, type: {}", event, event.getType());
        } catch (Exception e) {
            log.error("上下线消息处理失败", e);
        }
    }
}
