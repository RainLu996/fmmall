package com.lujun61.regist.controller;

import com.lujun61.beans.entity.User;
import com.lujun61.fmmall.vo.ResultVo;
import com.lujun61.regist.service.UserRegistService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserRegistController {

    @Resource
    private UserRegistService userRegistService;

    // http://localhost:9102/user/regist?username=lj&password=123456
    @PostMapping("/regist")
    public ResultVo registUser(@RequestBody User user) {

        return userRegistService.saveUser(user);

    }

}
