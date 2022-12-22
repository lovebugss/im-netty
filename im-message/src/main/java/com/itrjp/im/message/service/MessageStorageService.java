package com.itrjp.im.message.service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:50
 */
public interface MessageStorageService {
    /**
     * 保存无效的消息
     *
     * @param channelId
     * @param userId
     * @param message
     */
    void saveInvalidMessage(String channelId, String userId, String message);

    void saveMessage(String channelId, String userId, String message, String messageId);
}
