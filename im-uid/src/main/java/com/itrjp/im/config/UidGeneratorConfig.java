package com.itrjp.im.config;

import com.itrjp.im.uid.impl.CachedUidGenerator;
import com.itrjp.im.uid.worker.DisposableWorkerIdAssigner;
import com.itrjp.im.uid.worker.WorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * UidGeneratorConfig
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/17 23:12
 */
@Configuration
@Component
public class UidGeneratorConfig {

    /**
     * 用完即弃的WorkerIdAssigner, 依赖DB操作
     *
     * @return
     */
    @Bean
    WorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean
    CachedUidGenerator cachedUidGenerator() {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner());
        return cachedUidGenerator;
    }
}
