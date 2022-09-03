package com.lujun61.daemon.service.feign.fallback;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.OrderStatusUpdateClient;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateClientFallback implements OrderStatusUpdateClient {
    @Override
    public int orderStatusUpdate(Orders order) {

        System.out.println("OrderStatusUpdate服务降级");

        return 0;
    }
}
