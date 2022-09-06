package com.lujun61.order.controller;

import com.lujun61.beans.entity.Orders;
import com.lujun61.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @GetMapping("/get")
    public Orders queryOrder(@RequestParam("orderId") String orderId) {
        return orderService.queryOrderByOrderId(orderId);
    }

}

