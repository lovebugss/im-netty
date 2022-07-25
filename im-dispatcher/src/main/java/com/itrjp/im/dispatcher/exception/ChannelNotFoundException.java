package com.itrjp.im.dispatcher.exception;

import com.itrjp.common.exception.BizException;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/23 12:34
 */
public class ChannelNotFoundException extends BizException {
    public ChannelNotFoundException(String channelId) {
        super(5000, channelId + " Not Found");
    }
}
