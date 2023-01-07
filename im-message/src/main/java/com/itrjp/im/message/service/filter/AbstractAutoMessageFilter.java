package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;

/**
 * 自动
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 14:01
 */
public abstract class AbstractAutoMessageFilter implements MessageFilter {
    @Override
    public boolean matchFilterType(MessageFilterType type) {
        return MessageFilterType.AUTO.equals(type);
    }
}
