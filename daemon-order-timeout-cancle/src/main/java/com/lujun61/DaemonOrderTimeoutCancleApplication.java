package com.lujun61;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@EnableScheduling
public class DaemonOrderTimeoutCancleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaemonOrderTimeoutCancleApplication.class, args);
    }

}
