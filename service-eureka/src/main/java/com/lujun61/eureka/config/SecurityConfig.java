package com.lujun61.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description SpringSecurity配置文件
 * @author Jun Lu
 * @date 2022-08-29 19:01:35
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 禁止csrf网络攻击
        http.csrf().disable();

        //设置【当前服务器】的所有请求都要使⽤spring security的认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

    }

}
