package com.lujun61.fmmall.controller;

import com.lujun61.fmmall.service.UserAddrService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接⼝", tags = "收货地址管理")
@RequestMapping("/useraddr")
public class UserAddrController {

    @Resource
    private UserAddrService userAddrService;

    @ApiOperation("用户收货地址查询接口")
    @GetMapping("/list")
    @ApiImplicitParam(dataType = "String",name = "userId", value = "用户ID",required = true)
    public ResultVo listAddr(String userId, @RequestHeader("token") String token){
        return userAddrService.listAddrsByUid(userId);
    }

}
