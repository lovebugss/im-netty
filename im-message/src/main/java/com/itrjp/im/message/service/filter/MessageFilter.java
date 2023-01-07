package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.message.Message;
import com.itrjp.im.proto.message.MessageType;

/**
 * 消息过滤
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:56
 */
public interface MessageFilter {

    /**
     * 是否匹配过滤类型
     *
     * @param type
     * @return
     */
    boolean matchFilterType(MessageFilterType type);


    /**
     * 执行过滤
     *
     * @param message
     * @return
     */
    boolean doFilter(Message message);

    /**
     * 是否匹配消息类型
     *
     * @param type
     * @return
     */
    boolean matchMessageType(MessageType type);

}
