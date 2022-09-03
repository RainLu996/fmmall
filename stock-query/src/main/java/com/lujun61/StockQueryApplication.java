package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.lujun61.stockquery.dao")
@EnableEurekaClient
public class StockQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockQueryApplication.class, args);
    }

}
