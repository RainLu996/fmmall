package com.lujun61.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Jun Lu
 * @description 令牌校验拦截器：使用Redis作为分布式session校验Token是否过期。原理就是判断Redis中是否存在以Token为key的value。
 * @date 2022-08-20 21:28:48
 */
@Component
public class CheckTokenByRedisInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放⾏options请求
        // 浏览器向服务端发送OPTIONS预检请求，请求建立连接。需要放行
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {   // 忽略大小写
            return true;
        }

        // 习惯用请求头携带token信息
        String token = request.getHeader("token");

        // 前端传过来的null值可能是一个字符串
        // JSON传值时，只有字符串和数字，所以会将null转换为字符串的‘null’。
        if (token == null || "null".equalsIgnoreCase(token)) {
            ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_USER_NOT_SIGN_IN, "请先登录！", null);
            //提示请先登录
            doResponse(response, resultVO);
        } else {

            // 使用Redis验证token
            String userJson = stringRedisTemplate.boundValueOps(token).get();
            if (userJson == null) {   // 没有Token不放行
                ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_USER_NOT_SIGN_IN, "请重新登录！", null);
                doResponse(response, resultVO);
                return false;
            } else {                  // 有Token就续命
                stringRedisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);
                return true;
            }
        }
        return false;
    }


    private void doResponse(HttpServletResponse response, ResultVo resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = objectMapper.writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }
}
