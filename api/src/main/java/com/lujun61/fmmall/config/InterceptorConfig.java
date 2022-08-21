package com.lujun61.fmmall.config;

import com.lujun61.fmmall.interceptor.CheckTokenByRedisInterceptor;
import com.lujun61.fmmall.interceptor.ResetTokenExpireTimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //@Resource
    //private CheckTokenByJWTInterceptor checkTokenByJWTInterceptor;

    @Resource
    private CheckTokenByRedisInterceptor checkTokenByRedisInterceptor;

    @Resource
    private ResetTokenExpireTimeInterceptor resetTokenExpireTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(checkTokenByJWTInterceptor)     // 向Spring容器中注册拦截器
        //        .addPathPatterns(
        //                "/shopcart/**",
        //                "/useraddr/**",
        //                "/order/**"
        //        );   // 添加拦截路径

        registry.addInterceptor(checkTokenByRedisInterceptor)     // 向Spring容器中注册拦截器
                .addPathPatterns(
                        "/shopcart/**",
                        "/useraddr/**",
                        "/order/**"
                );   // 添加拦截路径

        registry.addInterceptor(resetTokenExpireTimeInterceptor)
                .addPathPatterns("/**");  // 拦截所有资源访问请求
    }

}
