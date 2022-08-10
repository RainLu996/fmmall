package com.lujun61.fmmall.controller;


import com.lujun61.beans.entity.ShoppingCart;
import com.lujun61.fmmall.service.ShopcartService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shopcart")
@Api(value = "提供购物车业务相关接口", tags = "购物车管理")
@CrossOrigin
public class ShopcartController {

    @Resource
    ShopcartService shopcartService;

    @PostMapping("/add")
    @ApiOperation("商品添加购物车接口")
    public ResultVo addShopCart(@RequestBody ShoppingCart shoppingCart,
                                @RequestHeader("token") String token) {
        return shopcartService.addProductToShopcart(shoppingCart);
    }


    @GetMapping("/list")
    @ApiOperation("查询用户购物车详细信息接口")
    @ApiImplicitParam(dataType = "String", name = "userId", value = "用户ID", required = true)
    public ResultVo listShopCart(String userId, @RequestHeader("token") String token) {
        return shopcartService.queryShopcartDetailInfoByUserId(userId);
    }


    @PutMapping("/update/{cartId}/{cartNum}")
    @ApiOperation("修改购物车中商品数量接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "cartId", value = "购物车主键", required = true),
            @ApiImplicitParam(dataType = "String", name = "cartNum", value = "商品个数的目标修改值", required = true)
    })
    public ResultVo updateShopCart(
            @PathVariable("cartId") String cartId, @PathVariable("cartNum") String cartNum, @RequestHeader("token") String token
    ) {
        return shopcartService.updateShopcartCartnumByCartId(cartId, cartNum);
    }

    @ApiOperation("查询所选中购物车中商品信息接口")
    @GetMapping("/list_bind_cids")
    @ApiImplicitParam(dataType = "String", name = "cids", value = "选择的购物⻋记录id", required = true)
    public ResultVo listByCids(String cids, @RequestHeader("token") String token) {
        return shopcartService.queryShopcartByCartIds(cids);
    }
}
