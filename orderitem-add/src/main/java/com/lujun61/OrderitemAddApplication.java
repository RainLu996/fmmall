package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.lujun61.orderitem.dao")
@EnableEurekaClient
public class OrderitemAddApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderitemAddApplication.class, args);
    }

}
