package com.itrjp.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 统计服务
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 15:57
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class StatApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatApplication.class, args);
    }
}
