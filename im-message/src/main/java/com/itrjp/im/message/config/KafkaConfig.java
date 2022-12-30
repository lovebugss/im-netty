package com.itrjp.im.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/27 23:01
 */
@EnableKafka
@Configuration
public class KafkaConfig {
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, byte[]> anotherKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setRecordInterceptor(new KafkaTraceRecordInterceptor<>());
//        return factory;
//    }
}
