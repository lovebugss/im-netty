package com.itrjp.common.consts;

/**
 * 缓存key常量
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 11:52
 */
public final class CacheKeyConstant {
    /**
     * connect 服务 节点负载.
     * <p>
     * 使用zset
     */
    public static final String CONNECT_NODE_LOAD = "im:stat:connect-node:load";
    /**
     * connect 服务 节点负载.
     */
    public static final String CONNECT_NODE = "im:stat:connect-node:load:";

    /**
     * 所有connect 节点信息
     * <p>
     * 使用hash
     */
    public static final String CONNECT_NODE_INFO = "im:stat:connect-node:info";

}
