package com.itrjp.im.connect.websocket.listener;

import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.proto.Packet;
import io.netty.channel.Channel;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/20 18:59
 */
public interface MessageListener {

    void onMessage(WebSocketClient client, Packet.Data data);
}
