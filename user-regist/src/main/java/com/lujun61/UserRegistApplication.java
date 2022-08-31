package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.lujun61.regist.dao")
@EnableEurekaClient
public class UserRegistApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRegistApplication.class, args);
    }

}
