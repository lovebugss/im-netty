package com.itrjp.common.consts;

/**
 * Kafka 常量
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 15:35
 */
public final class KafkaConstant {

    /**
     * 聊天消息topic, 用于消息广播
     */
    public static final String MESSAGE_TOPIC = "message-topic";
    public static final String CONNECT_TOPIC_PREFIX = "connect-topic-";
    /**
     * 上下线消息 topic, 允许丢失!!!
     */
    public static final String MESSAGE_JOIN_LEAVE_TOPIC = "message-join-leave-topic";
    /**
     * 通知消息
     */
    public static final String NOTICE_TOPIC = "notice-topic";

    /**
     * 存储
     */
    public static final String STORAGE_TOPIC = "storage-topic";
}
