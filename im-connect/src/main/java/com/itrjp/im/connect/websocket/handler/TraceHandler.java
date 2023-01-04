package com.itrjp.im.connect.websocket.handler;

import com.itrjp.common.trace.TraceUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/4 13:27
 */
@ChannelHandler.Sharable
public class TraceHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            TraceUtil.addTraceId();
            super.channelRead(ctx, msg);
        } finally {
            TraceUtil.clear();
        }

    }
}
