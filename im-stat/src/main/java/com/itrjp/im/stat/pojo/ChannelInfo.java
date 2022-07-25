package com.itrjp.im.stat.pojo;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 18:14
 */
public class ChannelInfo {

    private String channel;
    private long pv;
    private long maxPv;

    private long uv;
    private long maxUv;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getMaxPv() {
        return maxPv;
    }

    public void setMaxPv(long maxPv) {
        this.maxPv = maxPv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getMaxUv() {
        return maxUv;
    }

    public void setMaxUv(long maxUv) {
        this.maxUv = maxUv;
    }
}
