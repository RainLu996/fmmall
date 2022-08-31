package com.lujun61.regist.service.impl;

import com.lujun61.beans.entity.User;
import com.lujun61.regist.dao.UserMapper;
import com.lujun61.regist.service.UserSaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userSaveService")
public class UserSaveServiceImpl implements UserSaveService {

    @Resource
    UserMapper userMapper;

    @Override
    public Integer saveUser(User user) {

        return userMapper.insertUser(user);

    }
}
