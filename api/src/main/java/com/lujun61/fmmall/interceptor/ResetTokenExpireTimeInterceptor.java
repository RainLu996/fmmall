package com.lujun61.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/** 实现 分布式session/分布式会话
 * @author Jun Lu
 * @description 重置存储在Redis中的Token过期时间：此拦截器对于 受限资源/非受限资源 一律放行。
 *              加入了此拦截器，为了达到用户有Token发起请求就给Token重置过期时间，没有Token也放行的要求；
 *              需要要求前端在每个页面发送请求时，至少有一个请求携带用户的Token。
 * @date 2022-08-20 20:37:00
 */
@Component
public class ResetTokenExpireTimeInterceptor implements HandlerInterceptor {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 由于此拦截器对于无论什么请求都会放行，因此不需要单独对浏览器预检请求采取放行措施

        String token = request.getHeader("token");
        if (token != null) {
            String userJson = stringRedisTemplate.boundValueOps(token).get();

            if (userJson != null) {   // 来访用户Token有效，就给Token重置过期时间
                stringRedisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);
            }
        }

        return true;

    }
}
