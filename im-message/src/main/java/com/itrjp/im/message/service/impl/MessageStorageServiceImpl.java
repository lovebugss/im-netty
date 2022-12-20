package com.itrjp.im.message.service.impl;

import com.itrjp.im.message.service.MessageStorageService;
import org.springframework.stereotype.Service;

/**
 * MessageStorageService
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:58
 */
@Service
public class MessageStorageServiceImpl implements MessageStorageService {
    @Override
    public void saveInvalidMessage(String channelId, String userId, String message) {

    }

    /**
     * 保存消息
     *
     * @param channelId
     * @param userId
     * @param message
     * @param messageId
     */
    @Override
    public void saveMessage(String channelId, String userId, String message, long messageId) {

    }
}
