package com.itrjp.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * uid 启动类
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/17 22:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@MapperScan("com.itrjp.im.uid.worker.dao")
public class UIdApplication {
    public static void main(String[] args) {
        SpringApplication.run(UIdApplication.class, args);
    }
}
