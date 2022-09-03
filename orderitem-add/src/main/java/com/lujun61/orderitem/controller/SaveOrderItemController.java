package com.lujun61.orderitem.controller;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.orderitem.service.SaveOrderItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/order/item")
public class SaveOrderItemController {

    @Resource
    SaveOrderItemService saveOrderitemService;

    /**
     * @param list 购物车信息
     * @param orderId 订单id
     * @return java.lang.Integer
     * @description 保存订单快照接口
     * @author Jun Lu
     * @date 2022-09-01 20:14:12
     */
    @PostMapping("/add")
    public Integer addOrderItem
            (       @RequestBody List<DetailShoppingCart> list,
                    @RequestParam("orderId") String orderId
            ) {
        return saveOrderitemService.saveOrderitem(list, orderId);
    }
}