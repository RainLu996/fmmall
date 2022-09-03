package com.lujun61.daemon.service.feign;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.fallback.OrderTimeoutQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "order-timeout-query", fallback = OrderTimeoutQueryClientFallback.class)
public interface OrderTimeoutQueryClient {

    @GetMapping("/order/query/timeout")
    List<Orders> queryTimeoutOrder();

}
