package com.itrjp.im.connect.websocket.channel;

import com.itrjp.im.connect.websocket.HandshakeData;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.proto.Packet;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:08
 */
public class ChannelClient implements WebSocketClient {
    private final HandshakeData handshakeData;

    private final Channel channel;
    private final WebsocketChannel websocketChannel;

    public ChannelClient(HandshakeData handshakeData, Channel channel, WebsocketChannel websocketChannel) {
        this.handshakeData = handshakeData;
        this.channel = channel;
        this.websocketChannel = websocketChannel;
    }

    @Override
    public String getChannelId() {
        return websocketChannel.getChannelId();
    }

    @Override
    public WebsocketChannel getChannel() {
        return websocketChannel;
    }

    @Override
    public List<WebSocketClient> getAllClient(WebsocketChannel websocketChannel) {
        return null;
    }

    @Override
    public String getSession() {
        return channel.id().asLongText();
    }

    @Override
    public void leave() {
        websocketChannel.leave(this);
    }

    @Override
    public void join() {
        websocketChannel.join(this);
    }

    @Override
    public HandshakeData getHandshakeData() {
        return handshakeData;
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return getHandshakeData().parameters();
    }

    @Override
    public void sendMessage(Packet data) {
        channel.writeAndFlush(data);
    }

}
