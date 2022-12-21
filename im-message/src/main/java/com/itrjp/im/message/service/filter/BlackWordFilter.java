package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import org.springframework.stereotype.Service;

/**
 * 黑词过滤器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 14:03
 */
@Service
public class BlackWordFilter implements MessageFilter {
    @Override
    public boolean match(MessageFilterType type) {
        return MessageFilterType.BLACK.equals(type);
    }

    @Override
    public boolean doFilter(String message) {

        return true;
    }
}
