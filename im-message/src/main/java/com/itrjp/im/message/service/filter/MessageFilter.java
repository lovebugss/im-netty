package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.Message;

/**
 * 消息过滤
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:56
 */
public interface MessageFilter {

    boolean match(MessageFilterType type);

    boolean doFilter(Message message);
}
