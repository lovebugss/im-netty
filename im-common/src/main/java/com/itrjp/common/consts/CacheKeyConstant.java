package com.itrjp.common.consts;

/**
 * 缓存key常量
 * <p>
 * TODO key 格式: 服务名称:模块名称:业务名称[:业务id]
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
    public static final String CONNECT_NODE_LOAD = "im:connect-node:load";
    public static final String IM_STAT_CHANNEL_PV = "im:stat:channel:pv:";

    /**
     * connect 服务 节点负载.
     */
    public static final String CONNECT_NODE = "im:connect-node:load:";

    /**
     * 所有connect 节点信息
     * <p>
     * 使用hash
     */
    public static final String CONNECT_NODE_INFO = "im:connect-node:info";

    /**
     * <pre>
     * connect 节点与频道映射关系(hash)
     * key: connectNodeId
     * value: channelId
     * </pre>
     */
    public static final String CONNECT_NODE_CHANNEL_MAPPING = "im:connect-node:channel:mapping";
    /**
     * 频道用户列表
     */
    public static final String CHANNEL_USER = "im:channel:";
}
