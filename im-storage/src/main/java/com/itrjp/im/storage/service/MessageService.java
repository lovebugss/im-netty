package com.itrjp.im.storage.service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 10:37
 */
public interface MessageService {
    void handlerMessage(String channelId, String content);

    void handlerJoinLeaveMessage(String channelId, String userId, int type);
}
