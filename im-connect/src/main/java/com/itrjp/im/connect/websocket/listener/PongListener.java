package com.itrjp.im.connect.websocket.listener;

import com.itrjp.im.connect.websocket.WebSocketClient;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/20 19:02
 */
public interface PongListener {
    void onPong(WebSocketClient client);
}
