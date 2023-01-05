package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.Message;
import com.itrjp.im.proto.MessageType;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 18:50
 */
public interface AutoMessageFilter extends MessageFilter {
    @Override
    default boolean matchFilterType(MessageFilterType type) {
        return MessageFilterType.AUTO.equals(type);
    }

    /**
     * 匹配消息类型
     *
     * @param type
     * @return
     */
    boolean matchMessageType(MessageType type);

    boolean doMessageFilter(Message message);
}
