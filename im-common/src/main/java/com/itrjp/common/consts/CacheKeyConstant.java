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
    public static final String CONNECT_NODE_LIST = "im:connect-node:list";
    public static final String IM_STAT_CHANNEL_PV = "im:channel:stat:pv:";
    public static final String IM_STAT_CHANNEL_UV = "im:channel:stat:uv:";

    /**
     * connect 服务 节点负载.
     */
    public static final String IM_CONNECT_NODE_LOAD = "im:connect-node:load:";


    /**
     * 频道分布在的connect节点
     */
    public static final String IM_CHANNEL_CONNECT_NODE_MAPPING = "im:channel:connect-node:mapping:";

    /**
     * 频道用户列表
     */
    public static final String IM_CHANNEL_ONLINE_USER = "im:channel:online:user:";
}
