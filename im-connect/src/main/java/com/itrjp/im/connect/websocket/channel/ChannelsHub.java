package com.itrjp.im.connect.websocket.channel;

import com.itrjp.im.connect.websocket.WebSocketClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:18
 */
@Component
public class ChannelsHub {
    /**
     * 所有频道
     */
    private final Map<String, WebsocketChannel> channels = new ConcurrentHashMap<>();
    /**
     * session和channel映射
     */
    private final Map<String, String> channelMap = new ConcurrentHashMap<>();

    public WebsocketChannel create(String channelId, String sessionId) {
        channelMap.put(sessionId, channelId);
        return channels.computeIfAbsent(channelId,
                WebsocketChannel::new);
    }

    public void destroy(String sessionId) {
        String channelId = channelMap.remove(sessionId);
        int size = channelMap.values().size();
        if (channelId != null && size == 0) {
            channels.remove(channelId);
        }
    }

    public WebsocketChannel get(String channelId) {
        if (channelId == null) {
            return null;
        }
        return channels.get(channelId);
    }

    public Collection<WebsocketChannel> getAllChannel() {
        return channels.values();
    }

    public Collection<WebSocketClient> getAllClient(String channelId) {
        return channels.get(channelId).getAllClient();

    }

    public String getChannelBySessionId(String sessionId) {
        return channelMap.get(sessionId);
    }

    public WebSocketClient getWebSocketClient(String sessionId) {
        String channelId = channelMap.get(sessionId);
        WebsocketChannel websocketChannel = channels.get(channelId);
        return websocketChannel.getClient(sessionId);

    }
}
