package com.itrjp.im.api.entity;

import lombok.Data;

@Data
public class ChannelNodeInfo {
    private String address;
    private int port;
    private String protocol;
}
