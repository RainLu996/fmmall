package com.lujun61.daemon.service.feign;

import com.lujun61.daemon.service.feign.fallback.OrderCloseClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-close", fallback = OrderCloseClientFallback.class)
public interface OrderCloseClient {

    @GetMapping("/order/close")
    int closeOrder(@RequestParam("orderId") String orderId, @RequestParam("closeType") Integer closeType);

}
