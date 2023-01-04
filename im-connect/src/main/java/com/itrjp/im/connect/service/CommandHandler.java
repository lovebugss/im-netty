package com.itrjp.im.connect.service;

import com.google.protobuf.ByteString;
import com.itrjp.im.proto.CommandType;

/**
 * 系统命令处理器
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/4 10:52
 */
public interface CommandHandler {
    /**
     * 是否支持
     *
     * @param commandType
     * @return
     */
    boolean support(CommandType commandType);

    /**
     * 处理消息
     *
     * @param data
     */
    void handle(ByteString data);
}
