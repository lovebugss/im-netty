package com.itrjp.im.message.service;


import com.itrjp.im.proto.EventType;
import com.itrjp.im.proto.Message;

/**
 * MessageService
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:26
 */
public interface MessageService {
    /**
     * 处理消息
     * @param message
     * @return
     */
    String handlerMessage(Message message);

    /**
     * 处理上下线
     *
     * @param channelId
     * @param userId
     * @param type      0: 上线, 1: 下线
     */
    void handlerJoinLeave(String channelId, String userId, EventType type);
}
