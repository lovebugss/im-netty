package com.itrjp.im.connect.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/11 15:55
 */
@Component
public class WebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private final WebSocketProperties webSocketProperties;
    private final WebSocketServerInitializer serverInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    Class<? extends ServerChannel> channelClass;

    public WebSocketServer(WebSocketProperties webSocketProperties, WebSocketServerInitializer serverInitializer) {
        this.webSocketProperties = webSocketProperties;
        this.serverInitializer = serverInitializer;
    }


    /**
     * 启动
     */
    public void start() {
        startAsync().awaitUninterruptibly();
    }

    private Future<Void> startAsync() {
        initialization();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(channelClass)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(serverInitializer);

        InetSocketAddress addr = new InetSocketAddress(webSocketProperties.getPort());
        if (webSocketProperties.getHost() != null) {
            addr = new InetSocketAddress(webSocketProperties.getHost(), webSocketProperties.getPort());
        }
        return b.bind(addr)
                .addListener((future) -> {
                    if (future.isSuccess()) {
                        logger.info("Websocket server started at port: {}", webSocketProperties.getPort());
                    } else {
                        logger.info("Websocket server start failed at port: {}, error: {}", webSocketProperties.getPort(), future.cause().getMessage());
                    }
                });
    }

    private void initialization() {
        bossGroup = new NioEventLoopGroup(webSocketProperties.getBossNum());
        workerGroup = new NioEventLoopGroup(webSocketProperties.getWorkNum());
        channelClass = NioServerSocketChannel.class;
        serverInitializer.initialization(webSocketProperties);
    }

    public void stop() {
        logger.info("Websocket server stop...");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
