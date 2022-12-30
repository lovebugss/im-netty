package com.itrjp.im.api.entity;

import com.itrjp.im.proto.ChannelNodeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelConnectInfo {
    private String channelId;
    private List<ChannelNodeInfo> nodeInfo;
    private String token;
    private long time;
}
