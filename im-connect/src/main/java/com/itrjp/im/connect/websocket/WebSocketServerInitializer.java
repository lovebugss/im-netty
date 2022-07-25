package com.itrjp.im.connect.websocket;

import com.itrjp.im.connect.websocket.handler.AuthorizeHandler;
import com.itrjp.im.connect.websocket.handler.MessageHandler;
import com.itrjp.im.connect.websocket.handler.WebsocketHandler;
import com.itrjp.im.connect.websocket.listener.AuthorizationListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.springframework.stereotype.Component;


/**
 * WebSocketServerInitializer
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/11 16:11
 */
@Component
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    public static final String WEBSOCKET_AGGREGATOR = "websocketAggregator";
    private static final String SSL_HANDLER = "ssl";
    private static final String HTTP_SERVER_CODEC = "HttpServerCodec";
    public static final String AUTHORIZE_HANDLER = "authorizeHandler";
    public static final String WEBSOCKET_HANDLER = "websocketHandler";
    private WebSocketProperties webSocketProperties;
    private SslContext sslContext;

    private final AuthorizeHandler authorizeHandler;
    private final WebsocketHandler websocketHandler;
    private final MessageHandler messageHandler;
    private final AuthorizationListener authorization;

    public WebSocketServerInitializer(AuthorizeHandler authorizeHandler, WebsocketHandler websocketHandler, MessageHandler messageHandler, AuthorizationListener authorization) {
        this.authorizeHandler = authorizeHandler;
        this.websocketHandler = websocketHandler;
        this.messageHandler = messageHandler;
        this.authorization = authorization;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            pipeline.addLast(SSL_HANDLER, sslContext.newHandler(ch.alloc()));
        }

        addWebsocketHandler(pipeline);
    }

    private void addWebsocketHandler(ChannelPipeline pipeline) {
        pipeline.addLast(HTTP_SERVER_CODEC, new HttpServerCodec(8192, 8192, 8192));
        pipeline.addLast(new HttpObjectAggregator(webSocketProperties.getHttpMaxContextLength()));

        // 鉴权
        pipeline.addLast(AUTHORIZE_HANDLER, authorizeHandler);
        // websocket
        pipeline.addLast(WEBSOCKET_HANDLER, websocketHandler);
    }


    /**
     * 初始化
     *
     * @param webSocketProperties
     */
    public void initialization(WebSocketProperties webSocketProperties) {
        this.webSocketProperties = webSocketProperties;

        WebSocketProperties.SSLConfig sslConfig = webSocketProperties.getSsl();
        if (sslConfig.isEnable()) {
            sslContext = createSSLContext(sslConfig);
        }
    }

    /**
     * 创建ssl 证书.
     * TODO 支持自定义配置
     *
     * @param sslConfig
     * @return
     */
    private SslContext createSSLContext(WebSocketProperties.SSLConfig sslConfig) {
        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
