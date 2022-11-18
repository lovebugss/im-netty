package com.itrjp.im.dispatcher.service.impl;

import com.itrjp.im.dispatcher.exception.ChannelNotFoundException;
import com.itrjp.im.dispatcher.pojo.ConnectInfo;
import com.itrjp.im.dispatcher.service.ConnectService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/23 12:32
 */
@Service
public class ConnectServiceImpl implements ConnectService {

    @Override
    public ConnectInfo getConnectInfo(String channelId) {

        checkChannelConfig(channelId);

        // 获取最佳节点
        String url = getConnectNode(channelId);
        String token = createToken(channelId);
        return new ConnectInfo(url, token);
    }

    /**
     * 检查当前房间配置
     *
     * @param channelId
     */
    private void checkChannelConfig(String channelId) {

        // 检查频道是否存在
        if (!exits(channelId)) {
            throw new ChannelNotFoundException(channelId);
        }
        // 检查是否超过最大连接数

    }

    /**
     * TODO 生成 TOKEN
     *
     * @param channelId
     * @return
     */
    private String createToken(String channelId) {
        return "123";
    }

    /**
     * TODO 生成token
     *
     * @param channelId
     * @return
     */
    private String getConnectNode(String channelId) {

        return "localhost:18080/ws?cid=" + channelId;
    }

    /**
     * TODO 检查房间是否存在
     *
     * @param channelId
     * @return
     */
    private boolean exits(String channelId) {
        return true;
    }
}
