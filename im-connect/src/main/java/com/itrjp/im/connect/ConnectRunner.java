package com.itrjp.im.connect;

import com.itrjp.im.connect.websocket.WebSocketProperties;
import com.itrjp.im.connect.websocket.WebSocketServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 11:17
 */
@Component
public class ConnectRunner implements CommandLineRunner, DisposableBean {
    private final WebSocketServer server;
    private final WebSocketProperties webSocketProperties;

    public ConnectRunner(WebSocketServer server, WebSocketProperties webSocketProperties) {
        this.server = server;
        this.webSocketProperties = webSocketProperties;
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }

    @Override
    public void run(String... args) throws Exception {
        // 启动Websocket
        server.start();
        // 注册当前服务
        int port = webSocketProperties.getPort();
        String host = webSocketProperties.getHost();
    }
}
