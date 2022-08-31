package com.lujun61.checklogin.service.impl;

import com.lujun61.beans.entity.User;
import com.lujun61.checklogin.dao.UserMapper;
import com.lujun61.checklogin.service.UserCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userCheckService")
public class UserCheckServiceImpl implements UserCheckService {

    @Resource
    UserMapper userMapper;

    @Override
    public User check(String name) {
        System.out.println("UserCheckServiceImpl：" + name);

        return userMapper.selectUserByUsername(name);
    }
}
