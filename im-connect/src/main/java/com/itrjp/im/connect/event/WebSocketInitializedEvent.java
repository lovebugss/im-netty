package com.itrjp.im.connect.event;

import com.itrjp.im.connect.websocket.WebSocketProperties;
import org.springframework.context.ApplicationEvent;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 12:32
 */
public class WebSocketInitializedEvent extends ApplicationEvent {
    private final WebSocketProperties webSocketProperties;

    public WebSocketInitializedEvent(WebSocketProperties source) {
        super(source);
        this.webSocketProperties = source;
    }

    public WebSocketProperties getWebSocketProperties() {
        return webSocketProperties;
    }
}
