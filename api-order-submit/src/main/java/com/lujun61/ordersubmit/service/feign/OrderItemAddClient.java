package com.lujun61.ordersubmit.service.feign;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.ordersubmit.service.feign.fallback.OrderAddClientFallback;
import com.lujun61.ordersubmit.service.feign.fallback.OrderItemAddClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderitem-add", fallback = OrderItemAddClientFallback.class)
public interface OrderItemAddClient {

    @PostMapping("/order/item/add")
    Integer addOrderItem(@RequestBody List<DetailShoppingCart> list,
                    @RequestParam("orderId") String orderId
            );

}
