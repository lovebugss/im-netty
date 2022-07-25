package com.itrjp.im.connect.service;

import com.itrjp.im.connect.websocket.listener.MessageListener;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:53
 */
public interface MessageService extends MessageListener {
    /**
     * 广播聊天消息
     *
     * @param channelId
     * @param content
     */
    void broadcastMessage(String channelId, String content);

    /**
     * 广播上下线消息(是不是能用设计模式)
     *
     * @param channelId
     * @param userId
     * @param type
     */
    void broadcastJoinLeaveMessage(String channelId, String userId, int type);
}
