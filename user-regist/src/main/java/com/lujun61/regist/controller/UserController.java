package com.lujun61.regist.controller;

import com.lujun61.beans.entity.User;
import com.lujun61.regist.service.UserSaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "保存用户信息服务", tags = "用户管理")
public class UserController {


    @Resource
    UserSaveService userSaveService;

    @PostMapping("/save")
    @ApiOperation("用户信息保存接口")
    @ApiImplicitParam(dataType = "com.lujun61.beans.entity.User", name = "user", value = "用户信息", required = true)
    public Integer saveUserInfo(@RequestBody User user) {
        System.out.println("-----------------user-regist服务");

        return userSaveService.saveUser(user);
    }

}
