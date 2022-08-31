package com.lujun61.login.controller;

import com.lujun61.fmmall.vo.ResultVo;
import com.lujun61.login.service.UserLoginService;
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
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    // http://localhost:9100/user/login?username=rainlu&password=666666
    @ApiOperation("用户登录接口")   // ⽅法注解：说明接⼝⽅法的作⽤
    @ApiImplicitParams({    // ⽅法注解，说明接⼝⽅法的参数
            @ApiImplicitParam(dataType = "String", name = "username", value = "用户名", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户登录密码", required = true)
    })
    @GetMapping("/login")
    public ResultVo login(
            @RequestParam("username") String name,
            @RequestParam("password") String pwd
    ) {

        return userLoginService.checkLogin(name, pwd);

    }

}
