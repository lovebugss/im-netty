package com.itrjp.im.connect.event;

import com.itrjp.im.connect.websocket.WebSocketProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 12:34
 */
@Component
public class WebSocketInitializedEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public WebSocketInitializedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(WebSocketProperties webSocketProperties) {
        applicationEventPublisher.publishEvent(new WebSocketInitializedEvent(webSocketProperties));
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }
}
