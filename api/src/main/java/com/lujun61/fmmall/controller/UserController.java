package com.lujun61.fmmall.controller;

import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.beans.pojo.UserParams;
import com.lujun61.fmmall.service.UserService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
            @RequestParam("password") String pwd,
            HttpServletRequest request
    ) {

        return userService.checkLogin(name, pwd);

    }


    @ApiOperation("用户注册接口")   // ⽅法注解：说明接⼝⽅法的作⽤
    @ApiImplicitParams({    // ⽅法注解，说明接⼝⽅法的参数
            @ApiImplicitParam(dataType = "com.lujun61.beans.entity.User", name = "user", value = "用户注册账号", required = true),
    })
    @PostMapping("/regist")
    public ResultVo registUser(
            @RequestBody User user
            ) {
        return userService.userRegist(user.getUsername(), user.getPassword());
    }


    @ApiOperation("校验用户是否登录接口")   // ⽅法注解：说明接⼝⽅法的作⽤
    @GetMapping("/check")
    /**
     * @description 此接口只做用户有效性验证，不做具体业务
     * @author Jun Lu
     * @date 2022-09-13 09:02:25
     */
    public ResultVo checkInfo(@RequestHeader("token") String token) {
        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "注册成功！", null);
    }

    @ApiOperation("用户信息修改接口")
    @PutMapping("/update")
    public ResultVo updateUserInfo(@RequestHeader("token") String token,
                                   @RequestBody UserParams user
                                   ) {
        return userService.updateUser(user);
    }


    @ApiOperation("用户信息查询接口")
    @GetMapping("/query/{userId}")
    public ResultVo queryUserInfo(@RequestHeader("token") String token,
                                   @PathVariable("userId") String userId
    ) {
        return userService.queryUser(userId);
    }
}
