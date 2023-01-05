package com.itrjp.im.message.service;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.Message;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 15:23
 */
@Service
public interface MessageFilterService {
    boolean filter(Message message, MessageFilterType filterType);

}
