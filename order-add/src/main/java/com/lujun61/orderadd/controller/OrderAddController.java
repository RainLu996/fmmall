package com.lujun61.orderadd.controller;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.orderadd.service.OrderAddService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderAddController {

    @Resource
    OrderAddService orderAddService;

    @PostMapping("/save")
    public List<DetailShoppingCart> orderAdd(@RequestBody Orders order, @RequestParam("cids") String cids) {
        return orderAddService.insertOrder(order, cids);
    }

}
