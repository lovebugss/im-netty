package com.itrjp.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * TODO主入口
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 17:43
 */
@SpringBootApplication(scanBasePackages = "com.itrjp")
@EnableAutoConfiguration
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class ConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConnectApplication.class, args);
    }
}
