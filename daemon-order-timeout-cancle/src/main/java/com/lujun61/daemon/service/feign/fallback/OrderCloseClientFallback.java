package com.lujun61.daemon.service.feign.fallback;

import com.lujun61.daemon.service.feign.OrderCloseClient;
import org.springframework.stereotype.Component;

@Component
public class OrderCloseClientFallback implements OrderCloseClient {
    @Override
    public int closeOrder(String orderId, Integer closeType) {

        System.out.println("OrderClose服务降级");

        return 0;
    }
}
