package com.lujun61.orderclose.service.feign.fallback;

import com.lujun61.beans.entity.OrderItem;
import com.lujun61.orderclose.service.feign.OrderItemQueryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemQueryClientFallback implements OrderItemQueryClient {
    @Override
    public List<OrderItem> queryOrderItems(String orderId) {

        System.out.println("OrderItemQuery服务兜底方案~~~~~~");

        return null;
    }
}
