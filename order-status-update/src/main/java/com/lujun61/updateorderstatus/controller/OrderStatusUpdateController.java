package com.lujun61.updateorderstatus.controller;

import com.lujun61.beans.entity.Orders;
import com.lujun61.updateorderstatus.service.OrderStatusUpdateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order/status")
public class OrderStatusUpdateController {

    @Resource
    OrderStatusUpdateService orderStatusUpdateService;

    @PutMapping("/update")
    public int orderStatusUpdate(@RequestBody Orders order) {
        return orderStatusUpdateService.resetByOrderIdSelective(order);
    }

}
