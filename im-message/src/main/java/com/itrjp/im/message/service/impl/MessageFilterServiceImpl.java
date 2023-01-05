package com.itrjp.im.message.service.impl;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.message.service.MessageFilterService;
import com.itrjp.im.message.service.filter.MessageFilter;
import com.itrjp.im.proto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 16:38
 */
@Service
@RequiredArgsConstructor
public class MessageFilterServiceImpl implements MessageFilterService {
    private final List<MessageFilter> messageFilter;

    @Override
    public boolean filter(Message message, MessageFilterType filterType) {
        for (MessageFilter filter : messageFilter) {
            if (filter.match(filterType)) {
                return filter.doFilter(message);
            }
        }
        return false;
    }
}
