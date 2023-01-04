package com.itrjp.im.connect.service.impl;

import com.google.protobuf.ByteString;
import com.itrjp.im.connect.service.CommandHandler;
import com.itrjp.im.proto.CommandType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/4 11:06
 */
@Slf4j
@Service
public class SyncCommandHandler implements CommandHandler {
    @Override
    public boolean support(CommandType commandType) {
        return CommandType.SYNC.equals(commandType);
    }

    @Override
    public void handle(ByteString data) {
        log.info("收到同步命令");
    }
}
