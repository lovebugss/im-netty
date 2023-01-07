package com.itrjp.im.message.service.filter;

import com.itrjp.im.proto.message.Message;
import com.itrjp.im.proto.message.MessageType;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 18:58
 */
@Service
public class ImageAutoMessageFilter extends AbstractAutoMessageFilter {


    @Override
    public boolean matchMessageType(MessageType type) {
        return MessageType.IMAGE.equals(type);
    }

    @Override
    public boolean doFilter(Message message) {
        return false;
    }
}
