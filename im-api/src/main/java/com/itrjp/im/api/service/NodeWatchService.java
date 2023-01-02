package com.itrjp.im.api.service;

import com.itrjp.im.api.pojo.param.consul.ConsulWatchServiceEntity;

import java.util.List;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/2 15:58
 */
public interface NodeWatchService {
    void handlerServiceNotify(List<ConsulWatchServiceEntity> param);

}
