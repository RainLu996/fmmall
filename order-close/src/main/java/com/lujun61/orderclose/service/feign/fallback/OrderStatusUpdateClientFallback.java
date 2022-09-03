package com.lujun61.orderclose.service.feign.fallback;

import com.lujun61.beans.entity.Orders;
import com.lujun61.orderclose.service.feign.OrderStatusUpdateClient;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateClientFallback implements OrderStatusUpdateClient {
    @Override
    public int orderStatusUpdate(Orders order) {

        System.out.println("OrderStatusUpdate服务兜底方案~~~~~~");

        return 0;
    }
}
