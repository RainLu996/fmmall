package com.lujun61.fmmall.controller;


import com.lujun61.beans.entity.Orders;
import com.lujun61.fmmall.service.OrderService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@Api(value = "提供订单业务相关接口", tags = "订单管理")
@CrossOrigin
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping("/add")
    @ApiOperation("添加订单接口")
    @ApiImplicitParams({    // ⽅法注解，说明接⼝⽅法的参数
            @ApiImplicitParam(dataType = "com.lujun61.beans.entity.Orders", name = "order", value = "用户提交订单基本信息", required = true),
            @ApiImplicitParam(dataType = "String", name = "cids", value = "购物车商品id", required = true)
    })
    public ResultVo generateOrder(String cids, @RequestBody Orders order, @RequestHeader("token") String token) {


        return orderService.addOrder(cids, order);

    }

    @GetMapping("/status/{oid}")
    @ApiOperation("查询订单状态是否已支付接口")
    public ResultVo circleAccess(@PathVariable("oid") String orderId) {
        return orderService.queryOrderStatusByOrderId(orderId);
    }

    @GetMapping("/list")
    @ApiOperation("查询订单明细列表")
    public ResultVo list(@RequestHeader("token") String token, String userId, String status, int pageNum, int pageSize) {
        //return orderService.listDetailOrders(userId, status, pageNum, pageSize);

        return orderService.listDetailOrderItems(userId, status, pageNum, pageSize);
    }
}
