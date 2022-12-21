package com.itrjp.im.connect.websocket;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/11 15:57
 */
@ConfigurationProperties("websocket")
public class WebSocketProperties {
    private int bossNum = 0;
    private int workNum = 0;
    private String websocketPath = "/ws";

    private int httpMaxContextLength = 65535;
    private int maxFramePayloadLength = 65535;
    private int port = 18080;
    private String host;

    private SSLConfig ssl = new SSLConfig();

    public int getBossNum() {
        return bossNum;
    }

    public void setBossNum(int bossNum) {
        this.bossNum = bossNum;
    }

    public int getWorkNum() {
        return workNum;
    }

    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public SSLConfig getSsl() {
        return ssl;
    }

    public void setSsl(SSLConfig ssl) {
        this.ssl = ssl;
    }

    public int getHttpMaxContextLength() {
        return this.httpMaxContextLength;
    }

    public void setHttpMaxContextLength(int httpMaxContextLength) {
        this.httpMaxContextLength = httpMaxContextLength;
    }

    public int getMaxFramePayloadLength() {
        return maxFramePayloadLength;
    }

    public void setMaxFramePayloadLength(int maxFramePayloadLength) {
        this.maxFramePayloadLength = maxFramePayloadLength;
    }

    public String getWebsocketPath() {
        return websocketPath;
    }

    public void setWebsocketPath(String websocketPath) {
        this.websocketPath = websocketPath;
    }

    public static class SSLConfig {
        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
