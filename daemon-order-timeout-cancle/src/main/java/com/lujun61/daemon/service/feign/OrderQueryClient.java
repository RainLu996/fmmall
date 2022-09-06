package com.lujun61.daemon.service.feign;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.fallback.OrderQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-query-use-id", fallback = OrderQueryClientFallback.class)
public interface OrderQueryClient {

    @GetMapping("/order/get")
    Orders queryOrder(@RequestParam("orderId") String orderId);

}
