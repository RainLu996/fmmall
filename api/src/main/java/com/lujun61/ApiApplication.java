package com.lujun61;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
/* 在聚合工程中一样只能扫描跟 主类 同级及下级目录中的文件。
       就是说，项目包路径需要遵循命名约束。
       例如：com.lujun61就是 主类 所在的包，在其他模块中的包需要跟它 同级 或 下级 才可以被扫描到
*/
@MapperScan("com.lujun61.fmmall.dao")
@EnableOpenApi   // Swagger3.0需要在主启动类上加入此注解
@EnableScheduling
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
