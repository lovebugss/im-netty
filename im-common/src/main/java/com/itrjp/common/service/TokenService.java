package com.itrjp.common.service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/16 08:23
 */
public interface TokenService {
    boolean check(String token);

    /**
     * 创建token
     *
     * @return
     */
    String create(String channelId);
}
