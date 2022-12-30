package com.itrjp.im.connect.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 22:49
 */
@Data
@ConfigurationProperties("connect")
public class ConnectProperties {

    private Global global = new Global();

    @Data
    public static class Global {
        /**
         * 是否允许丢弃时间消息
         */
        boolean allowDropEvent = true;
    }
}
