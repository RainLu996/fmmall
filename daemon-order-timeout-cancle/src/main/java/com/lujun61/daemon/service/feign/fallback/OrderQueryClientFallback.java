package com.lujun61.daemon.service.feign.fallback;

import com.lujun61.beans.entity.Orders;
import com.lujun61.daemon.service.feign.OrderQueryClient;
import org.springframework.stereotype.Component;

@Component
public class OrderQueryClientFallback implements OrderQueryClient {
    @Override
    public Orders queryOrder(String orderId) {

        System.out.println("OrderQueryClient服务降级");

        return null;
    }
}
