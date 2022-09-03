package com.lujun61.orderclose.controller;

import com.lujun61.orderclose.service.OrderCloseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderCloseController {

    @Resource
    private OrderCloseService orderCloseService;

    @GetMapping("/close")
    public int closeOrder(@RequestParam("orderId") String orderId, @RequestParam("closeType") Integer closeType) {
        return orderCloseService.closeOrder(orderId, closeType);
    }

}
