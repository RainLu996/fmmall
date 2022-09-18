package com.lujun61;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//@EnableEurekaClient
@EnableConfigServer
public class ServiceConfigsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConfigsApplication.class, args);
    }

}
