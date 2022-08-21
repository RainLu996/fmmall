package com.lujun61.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.vo.ResultVo;
import io.jsonwebtoken.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description 令牌校验拦截器：使用JWT自带的异常判断来校验Token是否过期
 * @author Jun Lu
 * @date 2022-07-28 10:28:48
 */
@Configuration
public class CheckTokenByJWTInterceptor implements HandlerInterceptor {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放⾏options请求
        // 浏览器向服务端发送OPTIONS预检请求，请求建立连接。需要放行
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){   // 忽略大小写
            return true;
        }

        // 习惯用请求头携带token信息
        String token = request.getHeader("token");

        // 前端传过来的null值可能是一个字符串
        // JSON传值时，只有字符串和数字，所以会将null转换为了字符串的’null’。
        if (token == null || "null".equalsIgnoreCase(token)) {
            ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_USER_NOT_SIGN_IN, "请先登录！", null);
            //提示请先登录
            doResponse(response, resultVO);
        } else {
            try {
                // 使用JWT验证token
                JwtParser parser = Jwts.parser();
                //解析token的SigningKey必须和⽣成token时设置密码⼀致
                parser.setSigningKey("LuJun6666");
                //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_USER_SIGN_IN_TIMEOUT, "登录过期，请重新登录！", null);
                doResponse(response, resultVO);
            } catch (UnsupportedJwtException e) {
                ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_TOKEN_ILLEGAL, "Token不合法，请⾃重！", null);
                doResponse(response, resultVO);
            } catch (Exception e) {
                ResultVo resultVO = new ResultVo(Constants.LOGIN_FAIL_ELSE, "服务器繁忙，请稍后重试！", null);
                doResponse(response, resultVO);
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
