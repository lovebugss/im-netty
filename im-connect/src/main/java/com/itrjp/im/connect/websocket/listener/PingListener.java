package com.itrjp.im.connect.websocket.listener;

import com.itrjp.im.connect.websocket.WebSocketClient;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/20 19:01
 */
public interface PingListener {
    void onPing(WebSocketClient client);
}
