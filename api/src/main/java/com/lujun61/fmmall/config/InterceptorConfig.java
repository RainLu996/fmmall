package com.lujun61.fmmall.config;

import com.lujun61.fmmall.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)     // 向Spring容器中注册拦截器
                .addPathPatterns(
                        "/shopcart/**",
                        "/useraddr/**"
                );   // 添加拦截路径
    }

}
