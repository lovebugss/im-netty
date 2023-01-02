package com.itrjp.im.connect.service;

import com.google.protobuf.ByteString;
import com.itrjp.im.proto.DataType;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 18:15
 */
public interface MessageHandler {
    /**
     * 是否支持
     *
     * @param dataType
     * @return
     */
    boolean support(DataType dataType);

    /**
     * 处理消息
     *
     * @param from
     * @param to
     * @param timestamp
     * @param data
     */
    void handle(String from, String to, long timestamp, ByteString data);
}
