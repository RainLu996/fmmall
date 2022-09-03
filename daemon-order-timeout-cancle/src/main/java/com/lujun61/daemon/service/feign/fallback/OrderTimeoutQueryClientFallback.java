package com.lujun61.daemon.service.feign.fallback;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.OrderTimeoutQueryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTimeoutQueryClientFallback implements OrderTimeoutQueryClient {
    @Override
    public List<Orders> queryTimeoutOrder() {
        System.out.println("OrderTimeoutQueryClient服务降级");

        return null;
    }
}
