package com.itrjp.im.dispatcher.service;

import com.itrjp.im.dispatcher.pojo.ConsulWatchServiceEntity;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 15:28
 */
public interface NodeWatchService {
    void watch(List<Map<String, Object>> param);

    void handlerServiceNotify(List<ConsulWatchServiceEntity> param);
}
