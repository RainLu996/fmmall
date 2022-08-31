package com.lujun61.login.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.utils.MD5Utils;
import com.lujun61.fmmall.vo.ResultVo;
import com.lujun61.login.service.UserLoginService;
import com.lujun61.login.service.feign.UserCheckClient;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private UserCheckClient userCheckClient;

    @Override
    public ResultVo checkLogin(String name, String password) {
        // 1、微服务架构：使用check-login服务来查询用户信息
        User user = userCheckClient.getUserInfo(name);

        if (user == null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "用户不存在", null);
        } else {
            String md5 = MD5Utils.getMD5(password);

            if (md5.equals(user.getPassword())) {

                JwtBuilder builder = Jwts.builder();
                HashMap<String, Object> map = new HashMap<>();

                String token = builder.setSubject(name)   //主题，就是token中携带的数据
                        .setIssuedAt(new Date())          // 设置令牌创建时间
                        .setId(user.getUserId() + "")     //设置⽤户id为token令牌的id
                        .setClaims(map)                   // map中可以存放⽤户的⻆⾊权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) //设置过期时间
                        .signWith(SignatureAlgorithm.HS256, "LuJun6666")  //设置加密⽅式和加密密码
                        .compact();

                // 用户登录成功，就以Token为key，用户信息为value 保存至Redis中。实现分布式Session/会话
                try {
                    stringRedisTemplate.boundValueOps(token).set(objectMapper.writeValueAsString(user), 30, TimeUnit.MINUTES);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, token, user);
            } else {
                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "登录失败，密码错误", null);
            }
        }

    }
}

