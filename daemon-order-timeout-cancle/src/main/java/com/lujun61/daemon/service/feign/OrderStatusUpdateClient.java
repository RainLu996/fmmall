package com.lujun61.daemon.service.feign;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.fallback.OrderStatusUpdateClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-status-update", fallback = OrderStatusUpdateClientFallback.class)
public interface OrderStatusUpdateClient {

    @PutMapping("/order/status/update")
    int orderStatusUpdate(@RequestBody Orders order);

}
