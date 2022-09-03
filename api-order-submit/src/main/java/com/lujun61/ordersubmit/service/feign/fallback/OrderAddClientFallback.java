package com.lujun61.ordersubmit.service.feign.fallback;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.ordersubmit.service.feign.OrderAddClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderAddClientFallback implements OrderAddClient {
    @Override
    public List<DetailShoppingCart> orderAdd(Orders order, String cids) {
        System.out.println("api-order-submit中OrderAdd服务兜底方案");

        return null;
    }
}
