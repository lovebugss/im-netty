package com.itrjp.im.stat.service;

/**
 * 服务器节点统计
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:00
 */
public interface NodeStatService {

    /**
     * 连接
     */
    void connected(String nodeName, String sessionId);

    /**
     * 断开连接
     */
    void disConnected(String nodeName, String sessionId);


    /**
     * 服务启动
     */
    void start(String id);

    /**
     * 服务停止
     */
    void stop(String id);

}
