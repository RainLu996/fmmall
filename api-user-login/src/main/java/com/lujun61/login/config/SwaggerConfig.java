package com.lujun61.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description 使用javaConfig配置方式配置Swagger
 * @author Jun Lu
 * @date 2022-07-19 20:24:00
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /* swagger会帮助我们⽣成接⼝⽂档，但是需要开发者提供相应信息及规则
     *    1：配置⽣成的⽂档信息
     *    2: 配置⽣成规则
     */

    @Bean
    public Docket getDocket() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《锋迷商城》后端接口说明")
                .description("此⽂档详细说明了锋迷商城项⽬后端接⼝规范...")
                .version("v 2.1.1")
                .contact( new Contact("陆俊","www.baidu.com","lujun61@outlook.com") );

        ApiInfo apiInfo = apiInfoBuilder.build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo) // 指定⽣成的⽂档中的封⾯信息：⽂档标题、版本、作者
                .select()
                // 指定需要生成API文档的包
                .apis(RequestHandlerSelectors.basePackage("com.lujun61.fmmall.controller"))
                .paths(PathSelectors.any())  // 指定为哪种url请求生成文档
                .build();
    }
}
