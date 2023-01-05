package com.itrjp.im.message.service.filter;

import com.itrjp.common.enums.MessageFilterType;
import com.itrjp.im.proto.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 黑词过滤器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 14:03
 */
@Service
public class BlackWordFilter implements MessageFilter {

    private final ACFilter filter = new ACFilter();

    /**
     * TODO 从数据库加载黑词库
     */
    @PostConstruct
    public void init() {
        filter.addWord("你妈的");
        filter.addWord("傻逼");
        filter.build();
    }

    @Override
    public boolean match(MessageFilterType type) {
        return false;
    }

    @Override
    public boolean doFilter(Message message) {
        return filter.contains(message.getContent());
    }
}
