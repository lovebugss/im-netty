package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.message.Message;
import com.itrjp.im.proto.message.MessageType;
import org.springframework.stereotype.Service;

/**
 * 人工审核
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 17:28
 */
@Service
public class ManualMessageFilter implements MessageFilter {

    @Override
    public boolean matchFilterType(MessageFilterType type) {
        return MessageFilterType.MANUAL.equals(type);
    }

    @Override
    public boolean doFilter(Message message) {
        return false;
    }

    @Override
    public boolean matchMessageType(MessageType type) {
        return true;
    }
}
