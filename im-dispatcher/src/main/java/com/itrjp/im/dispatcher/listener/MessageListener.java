package com.itrjp.im.dispatcher.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.itrjp.common.consts.KafkaConstant.MESSAGE_TOPIC;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:38
 */
@Component
public class MessageListener {
    @KafkaListener(topics = {MESSAGE_TOPIC})
    public void onMessage(byte[] data) {

    }
}
