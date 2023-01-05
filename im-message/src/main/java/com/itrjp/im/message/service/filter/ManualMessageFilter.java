package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.Message;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 17:28
 */
@Service
public class ManualMessageFilter implements MessageFilter {

    @Override
    public boolean match(MessageFilterType type) {
        return MessageFilterType.MANUAL.equals(type);
    }

    @Override
    public boolean doFilter(Message message) {
        return false;
    }
}
