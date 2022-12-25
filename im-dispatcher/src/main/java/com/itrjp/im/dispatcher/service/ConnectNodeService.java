package com.itrjp.im.dispatcher.service;

import com.itrjp.im.dispatcher.entity.ConnectNode;

import java.util.List;

/**
 * connect node service
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 15:52
 */
public interface ConnectNodeService {

    /**
     * 服务启动
     */
    void online(ConnectNode connectNode);

    /**
     * 服务停止
     */
    void offline(ConnectNode connectNode);

    /**
     * 获取可用列表
     *
     * @return
     */
    List<ConnectNode> getAvailableList();

}
