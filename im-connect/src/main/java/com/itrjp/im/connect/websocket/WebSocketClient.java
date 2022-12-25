package com.itrjp.im.connect.websocket;

import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.proto.Packet;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:09
 */
public interface WebSocketClient {
    String getChannelId();

    WebsocketChannel getChannel();

    List<WebSocketClient> getAllClient(WebsocketChannel websocketChannel);

    String getSession();

    void leave();

    void join();

    HandshakeData getHandshakeData();

    Map<String, List<String>> getParameters();

    void sendMessage(Packet data);
}
