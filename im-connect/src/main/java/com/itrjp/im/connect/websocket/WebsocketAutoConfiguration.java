package com.itrjp.im.connect.websocket;

import com.itrjp.im.connect.websocket.listener.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebsocketAutoConfiguration
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/11 18:59
 */
@Configuration
@EnableConfigurationProperties(value = {WebSocketProperties.class})
public class WebsocketAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(WebsocketAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    AuthorizationListener authorization() {
        return new DefaultAuthorization();
    }

    @Bean
    @ConditionalOnMissingBean
    OpenListener openListener() {
        return webSocketClient -> logger.info("onOpen");
    }

    @Bean
    @ConditionalOnMissingBean
    CloseListener closeListener() {
        return webSocketClient -> logger.info("onClose");
    }

    @Bean
    @ConditionalOnMissingBean
    PingListener pingListener() {
        return webSocketClient -> logger.info("ping");
    }

    @Bean
    @ConditionalOnMissingBean
    PongListener pongListener() {
        return webSocketClient -> logger.info("pong");
    }

    @Bean
    @ConditionalOnMissingBean
    MessageListener messageListener() {
        return (webSocketClient, protobuf) -> logger.info("onMessage, message: {}", protobuf);
    }

    @Bean
    @ConditionalOnMissingBean
    ExceptionListener exceptionListener() {
        return (client, throwable) -> logger.error("onException", throwable);
    }
}
