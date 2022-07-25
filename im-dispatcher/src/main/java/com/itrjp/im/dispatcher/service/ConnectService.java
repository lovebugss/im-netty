package com.itrjp.im.dispatcher.service;

import com.itrjp.im.dispatcher.pojo.ConnectInfo;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/23 12:29
 */
public interface ConnectService {
    /**
     * 获取连接信息
     *
     * @param channelId
     * @return
     */
    ConnectInfo getConnectInfo(String channelId);
}
