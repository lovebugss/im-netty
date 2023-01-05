package com.itrjp.im.message.service.filter;

import com.itrjp.im.proto.Message;
import com.itrjp.im.proto.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/5 18:54
 */
@Slf4j
@Service
public class TextAutoMessageFilter extends AbstractAutoMessageFilter {

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


    protected TextAutoMessageFilter(List<AutoMessageFilter> autoMessageFilters) {
        super(autoMessageFilters);
    }

    @Override
    public boolean matchMessageType(MessageType type) {
        return MessageType.TEXT.equals(type);
    }

    @Override
    public boolean doMessageFilter(Message message) {
        return filter.contains(message.getContent());
    }

}
