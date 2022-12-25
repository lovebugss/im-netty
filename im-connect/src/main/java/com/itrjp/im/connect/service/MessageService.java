package com.itrjp.im.connect.service;

import com.itrjp.im.connect.websocket.listener.MessageListener;
import com.itrjp.im.proto.Data;
import com.itrjp.im.proto.Event;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:53
 */
public interface MessageService extends MessageListener {
    /**
     * 广播聊天消息
     */
    void broadcastMessage(String channelId, Data data);

    /**
     * 广播上下线消息(是不是能用设计模式)
     *
     * @param channelId
     * @param event
     */
    void broadcastEvent(String channelId, Event event);
}
