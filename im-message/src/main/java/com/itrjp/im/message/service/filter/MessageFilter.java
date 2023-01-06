package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.message.Message;

/**
 * 消息过滤
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:56
 */
public interface MessageFilter {

    boolean matchFilterType(MessageFilterType type);

    boolean doFilter(Message message);
}
