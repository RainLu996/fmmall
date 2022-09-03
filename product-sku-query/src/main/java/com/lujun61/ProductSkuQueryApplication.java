package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lujun61.productsku.dao")
public class ProductSkuQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductSkuQueryApplication.class, args);
    }

}
