package com.itrjp.im.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/19 20:57
 */
@SpringBootApplication(scanBasePackages = "com.itrjp")
@EnableAutoConfiguration
@EnableDiscoveryClient
public class APIApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
    }
}
