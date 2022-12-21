package com.itrjp.im.api.service.impl;

import com.itrjp.im.api.entity.ChannelNodeInfo;
import com.itrjp.im.api.service.ConnectNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectNodeServiceImpl implements ConnectNodeService {
    @Override
    public List<ChannelNodeInfo> getBestNode(String channelId) {
        return null;
    }

}
