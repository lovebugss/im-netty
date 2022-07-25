package com.itrjp.im.connect.service;

import com.itrjp.im.connect.websocket.listener.CloseListener;
import com.itrjp.im.connect.websocket.listener.OpenListener;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 21:30
 */
public interface ChannelService extends OpenListener, CloseListener {
}
