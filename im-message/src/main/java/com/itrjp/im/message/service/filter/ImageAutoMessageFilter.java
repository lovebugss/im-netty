package com.itrjp.im.message.service.filter;

import com.itrjp.im.proto.Message;
import com.itrjp.im.proto.MessageType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 18:58
 */
@Service
public class ImageAutoMessageFilter extends AbstractAutoMessageFilter {

    protected ImageAutoMessageFilter(List<AutoMessageFilter> autoMessageFilters) {
        super(autoMessageFilters);
    }

    @Override
    public boolean matchMessageType(MessageType type) {
        return MessageType.IMAGE.equals(type);
    }

    @Override
    public boolean doMessageFilter(Message message) {
        return false;
    }
}
