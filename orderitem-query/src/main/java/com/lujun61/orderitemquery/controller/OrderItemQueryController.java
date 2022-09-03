package com.lujun61.orderitemquery.controller;

import com.lujun61.beans.entity.OrderItem;
import com.lujun61.orderitemquery.service.OrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orderitem")
public class OrderItemQueryController {

    @Resource
    OrderItemService orderItemService;

    @GetMapping("/query")
    public List<OrderItem> queryOrderItems(@RequestParam("orderId") String orderId) {
        return orderItemService.queryOrderItems(orderId);
    }

}
