package com.itrjp.common.config;

import com.itrjp.common.cache.MemoryStore;
import com.itrjp.common.cache.Store;
import com.itrjp.common.entity.Channel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreConfig {
    @Bean
    @ConditionalOnMissingBean
    Store<Channel> channelStore() {
        return new MemoryStore<>();
    }
}
