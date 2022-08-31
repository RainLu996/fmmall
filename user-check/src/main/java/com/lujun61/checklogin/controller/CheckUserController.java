package com.lujun61.checklogin.controller;

import com.lujun61.beans.entity.User;
import com.lujun61.checklogin.service.UserCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "校验用户信息服务", tags = "用户管理")
public class CheckUserController {

    @Resource
    UserCheckService userCheckService;

    @GetMapping("/check")
    @ApiOperation("用户信息查询接口")
    @ApiImplicitParam(dataType = "String", name = "username", value = "用户名", required = true)
    public User getUserInfo(@RequestParam("username") String name) {

        System.out.println("-----------------user-check服务");


        return userCheckService.check(name);
    }

}
