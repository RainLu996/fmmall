package com.lujun61.fmmall.controller;


import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopcart")
@Api(value = "提供购物车业务相关接口", tags = "购物车管理")
@CrossOrigin
public class ShopcartController {

    @GetMapping("/list")
    public ResultVo listCarts() {
        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", null);
    }

}
