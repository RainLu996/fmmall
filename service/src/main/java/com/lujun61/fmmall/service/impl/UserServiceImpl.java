package com.lujun61.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.UserMapper;
import com.lujun61.fmmall.service.UserService;
import com.lujun61.fmmall.utils.MD5Utils;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.fmmall.vo.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service("userService")
@Scope("singleton")  // 单例模式：目的是使得 line27 中被锁住的【this】是同一个service对象
                     // 当然，如果对Controller使用单例作用域，效果一样。因为同一个Controller必定使用同一个Service对象
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ObjectMapper objectMapper;

    @Transactional     // 注册用户需要使用事务
    public ResultVo userRegist(String name, String password) {

        synchronized (this) {
            User user = userMapper.selectUserByName(name);

            if (user == null) {

                user = new User();
                user.setUserId(UUIDUtils.getUUID());
                user.setUsername(name);
                user.setPassword(MD5Utils.getMD5(password));
                user.setUserImg("images/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());

                int i = userMapper.insertUser(user);

                if (i > 0) {
                    return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "注册成功！", null);
                } else {
                    return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "系统繁忙，请稍后重试……", null);
                }

            } else {

                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "用户名已被注册！", null);

            }
        }
    }

    @Transactional
    public ResultVo checkLogin(String name, String password) {

        User user = userMapper.selectUserByName(name);

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
