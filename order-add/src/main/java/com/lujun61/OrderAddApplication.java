package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
@MapperScan("com.lujun61.orderadd.dao")
public class OrderAddApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderAddApplication.class, args);
    }

}
