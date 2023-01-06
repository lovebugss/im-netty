package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.message.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自动
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 14:01
 */
@Service
public abstract class AbstractAutoMessageFilter implements AutoMessageFilter {
    private final List<AutoMessageFilter> autoMessageFilters;


    protected AbstractAutoMessageFilter(List<AutoMessageFilter> autoMessageFilters) {
        this.autoMessageFilters = autoMessageFilters;
    }

    @Override
    public boolean matchFilterType(MessageFilterType type) {
        return MessageFilterType.AUTO.equals(type);
    }

    @Override
    public boolean doFilter(Message message) {
        for (AutoMessageFilter autoMessageFilter : autoMessageFilters) {
            if (autoMessageFilter.matchMessageType(message.getType())) {
                return autoMessageFilter.doMessageFilter(message);
            }
        }
        return false;
    }
}
