package com.lujun61.orderclose.service.feign;

import com.lujun61.beans.entity.OrderItem;
import com.lujun61.orderclose.service.feign.fallback.OrderItemQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderitem-query", fallback = OrderItemQueryClientFallback.class)
public interface OrderItemQueryClient {

    @GetMapping("/orderitem/query")
    List<OrderItem> queryOrderItems(@RequestParam("orderId") String orderId);

}
