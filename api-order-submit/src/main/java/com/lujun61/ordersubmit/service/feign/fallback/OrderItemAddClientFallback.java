package com.lujun61.ordersubmit.service.feign.fallback;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.ordersubmit.service.feign.OrderItemAddClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemAddClientFallback implements OrderItemAddClient {
    @Override
    public Integer addOrderItem(List<DetailShoppingCart> list, String orderId) {
        System.out.println("api-order-submit中OrderItemAdd服务兜底方案");

        return null;
    }
}
