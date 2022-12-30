package com.itrjp.im.connect.listener;

import com.itrjp.common.trace.TraceUtil;
import com.itrjp.im.connect.service.EventHandlerService;
import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.proto.Data;
import com.itrjp.im.proto.Event;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Kafka 监听器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:49
 */
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    private final MessageService messageService;
    private final List<EventHandlerService> eventHandlerService;

    /**
     * 消息
     *
     * @param data
     */
    @KafkaListener(topics = {"${im.connect-topic}"})
    public void onMessage(byte[] data, @Header("traceId") String traceId) {
        try {
            if (traceId != null) {
                TraceUtil.setTraceId(traceId);
            }
            Data d = Data.parseFrom(data);
            logger.info("接受Kafka消息: {}", d);
            messageService.broadcastMessage(d.getChannelId(), d);
        } catch (Exception e) {
            logger.error("消息处理失败", e);
        } finally {
            TraceUtil.clear();
        }
    }

    /**
     * 上下线消息
     *
     * @param data
     */
    @KafkaListener(topics = {"${im.connect-notify-topic}"})
    public void onNotify(byte[] data, @Header("traceId") String traceId) {
        try {
            if (traceId != null) {
                TraceUtil.setTraceId(traceId);
            }
            Event event = Event.parseFrom(data);
            logger.info("接受Kafka上下线消息: {}, type: {}", event, event.getType());
            for (EventHandlerService handler : eventHandlerService) {
                if (handler.support(event.getType())) {
                    handler.handle(event);
                }
            }
        } catch (Exception e) {
            logger.error("上下线消息处理失败", e);
        } finally {
            TraceUtil.clear();
        }
    }
}
