package com.itrjp.im.message.service.impl;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.service.MessageFilterService;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 16:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageFilterServiceImpl implements MessageFilterService {
    private final List<MessageFilter> messageFilter;

    @Override
    public boolean filter(Message message, MessageFilterType filterType) {
        for (MessageFilter filter : messageFilter) {
            // 先匹配过滤器类型, 再匹配消息类型
            if (filter.matchFilterType(filterType) && filter.matchMessageType(message.getType())) {
                log.info("消息过滤器匹配成功, 过滤器类型: {}, 消息类型: {}", filterType, message.getType());
                return filter.doFilter(message);
            }
        }
        return false;
    }
}
