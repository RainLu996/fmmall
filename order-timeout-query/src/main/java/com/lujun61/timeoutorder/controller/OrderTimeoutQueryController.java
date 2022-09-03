package com.lujun61.timeoutorder.controller;

import com.lujun61.beans.entity.Orders;
import com.lujun61.timeoutorder.service.OrderTimeoutQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderTimeoutQueryController {

    @Resource
    OrderTimeoutQueryService orderTimeoutQueryService;

    @GetMapping("/query/timeout")
    public List<Orders> queryTimeoutOrder() {
        return orderTimeoutQueryService.queryTimeoutOrder();
    }

}
