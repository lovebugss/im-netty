package com.itrjp.im.connect.websocket.listener;

import com.itrjp.im.connect.websocket.WebSocketClient;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/20 19:00
 */
public interface CloseListener {
    void onClose(WebSocketClient webSocketClient);
}
