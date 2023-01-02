package com.itrjp.common.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;

/**
 * ProtobufUtil
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 16:40
 */
@Slf4j
public class ProtobufUtils {

    public static String toJson(MessageOrBuilder messageOrBuilder) {
        try {
            return JsonFormat.printer().print(messageOrBuilder);
        } catch (InvalidProtocolBufferException e) {
            log.warn("ProtobufUtils.toJson error", e);
            return null;
        }
    }

    public static Message fromJson(String json, Message.Builder messageOrBuilder) {
        try {
            JsonFormat.parser().ignoringUnknownFields().merge(json, messageOrBuilder);
            return messageOrBuilder.build();
        } catch (InvalidProtocolBufferException e) {
            log.warn("ProtobufUtils.fromJson error", e);
        }
        return null;
    }
}
