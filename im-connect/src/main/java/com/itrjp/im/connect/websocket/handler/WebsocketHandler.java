package com.itrjp.im.connect.websocket.handler;

import com.google.protobuf.MessageLiteOrBuilder;
import com.itrjp.im.connect.websocket.HandshakeData;
import com.itrjp.im.connect.websocket.WebSocketProperties;
import com.itrjp.im.connect.websocket.channel.ChannelClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.connect.websocket.listener.ExceptionListener;
import com.itrjp.im.connect.websocket.listener.OpenListener;
import com.itrjp.im.connect.websocket.listener.PingListener;
import com.itrjp.im.connect.websocket.listener.PongListener;
import com.itrjp.im.proto.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.itrjp.im.connect.websocket.WebSocketServerInitializer.AUTHORIZE_HANDLER;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/11 17:37
 */
@Component
@ChannelHandler.Sharable
public class WebsocketHandler extends SimpleChannelInboundHandler<Object> {
    private final Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebSocketProperties webSocketProperties;
    private final OpenListener openListener;
    private final MessageHandler messageHandler;
    private final PingListener pingListener;
    private final PongListener pongListener;
    private final ExceptionListener exceptionListener;
    private final ChannelsHub channelsHub;

    public WebsocketHandler(WebSocketProperties webSocketProperties, OpenListener openListener,
                            MessageHandler messageHandler, PingListener pingListener, PongListener pongListener,
                            ExceptionListener exceptionListener, ChannelsHub channelsHub) {
        this.webSocketProperties = webSocketProperties;
        this.openListener = openListener;
        this.messageHandler = messageHandler;
        this.pingListener = pingListener;
        this.pongListener = pongListener;
        this.exceptionListener = exceptionListener;
        this.channelsHub = channelsHub;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("WebsocketHandler#channelRead, channel: [{}], message class: [{}]", ctx.channel().id(), msg.getClass());
        if (msg instanceof CloseWebSocketFrame) {
            logger.info("WebsocketHandler#channelRead CloseWebSocketFrame");
            ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
        } else if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        logger.info("WebsocketHandler#handleHttpRequest");
        // Handshake
        handshake(ctx, req, ctx.channel());
    }

    private void handshake(ChannelHandlerContext ctx, FullHttpRequest req, Channel channel) {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true, this.webSocketProperties.getMaxFramePayloadLength());
        final WebSocketServerHandshaker handshake = wsFactory.newHandshaker(req);
        if (handshake == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            ChannelPipeline pipeline = ctx.pipeline();

            handshake.handshake(channel, req)
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            logger.info("handshake success");
                            // 握手成功
                            addWebSocketHandlers(ctx, pipeline);
                            connectClient(req, channel);
                        } else {
                            logger.error("handshake failed", future.cause().fillInStackTrace());
                            handshake.close(channel, new CloseWebSocketFrame());
                        }
                    });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("exceptionCaught", cause);
        exceptionListener.onException(channelsHub.getWebSocketClient(ctx.channel().id().asLongText()), cause);
        ctx.close();
    }

    private void connectClient(FullHttpRequest request, Channel channel) {
        // 创建频道
        // 加入频道
        QueryStringDecoder queryString = new QueryStringDecoder(request.uri());
        Map<String, List<String>> parameters = queryString.parameters();
        HandshakeData handshakeData = new HandshakeData(parameters, request.uri());
        WebsocketChannel websocketChannel = channelsHub.create(parameters.get("cid").get(0), channel.id().asLongText());
        ChannelClient channelClient = new ChannelClient(handshakeData, channel, websocketChannel);
        channelClient.join();
        // 调用状态服务

        openListener.onOpen(channelClient);
    }

    private void addWebSocketHandlers(ChannelHandlerContext ctx, ChannelPipeline pipeline) {
        pipeline.remove(ctx.name());
        pipeline.remove(AUTHORIZE_HANDLER);
        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketFrameAggregator(Integer.MAX_VALUE));

        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());

        //将WebSocketFrame转为ByteBuf 以便后面的 ProtobufDecoder 解码
        pipeline.addLast(new MessageToMessageDecoder<WebSocketFrame>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
                ByteBuf byteBuf = msg.content();
                if (msg instanceof BinaryWebSocketFrame) {
                    // 二进制消息
                    byteBuf.retain();
                    out.add(msg.content());
                } else if (msg instanceof TextWebSocketFrame) {
                    // 二进制消息
                    byteBuf.retain();
                    out.add(((TextWebSocketFrame) msg).content());
                } else if (msg instanceof PingWebSocketFrame) {
                    pingListener.onPing(channelsHub.getWebSocketClient(ctx.channel().id().asLongText()));
                } else if (msg instanceof PongWebSocketFrame) {
                    pongListener.onPong(channelsHub.getWebSocketClient(ctx.channel().id().asLongText()));
                }
            }
        });

        pipeline.addLast(new ProtobufDecoder(Packet.getDefaultInstance()));
        //自定义入站处理
        pipeline.addLast(messageHandler);
        //出站处理 将protoBuf实例转为WebSocketFrame
        pipeline.addLast(new ProtobufEncoder() {
            @Override
            protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
                Packet mpMsg = (Packet) msg;
                WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(mpMsg.toByteArray()));
                out.add(frame);
            }
        });
    }


    private String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + webSocketProperties.getWebsocketPath();
        if (webSocketProperties.getSsl().isEnable()) {
            return "wss://" + location;
        } else {
            return "ws://" + location;
        }
    }
}
