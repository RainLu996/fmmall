package com.lujun61.fmmall.controller;

import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.service.UserService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "提供用户登录与注册入口", tags = "用户管理")  // 类注解，在控制器类添加此注解，可以对控制器类进⾏功能说明
@CrossOrigin   // 前后端分离，后端解决跨域请求问题
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation("用户登录接口")   // ⽅法注解：说明接⼝⽅法的作⽤
    @ApiImplicitParams({    // ⽅法注解，说明接⼝⽅法的参数
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户登录密码", required = true)
    })
    @GetMapping("/login")
    public ResultVo login(
            @RequestParam("username") String name,
            @RequestParam("password") String pwd
    ) {

        return userService.checkLogin(name, pwd);

    }


    @ApiOperation("用户注册接口")   // ⽅法注解：说明接⼝⽅法的作⽤
    @ApiImplicitParams({    // ⽅法注解，说明接⼝⽅法的参数
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户注册账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户注册密码", required = true)
    })
    @PostMapping("/regist")
    public ResultVo registUser(
            @RequestBody User user
            ) {
        return userService.userRegist(user.getUsername(), user.getPassword());
    }

}
