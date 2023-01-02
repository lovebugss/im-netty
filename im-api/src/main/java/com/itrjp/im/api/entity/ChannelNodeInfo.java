package com.itrjp.im.api.entity;

import lombok.Data;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 10:27
 */
@Data
public class ChannelNodeInfo {
    private String address;
    private String protocol;
    private int port;
}
