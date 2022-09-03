package com.lujun61.orderadd.service.feign.fallback;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.orderadd.service.feign.StockQueryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockQueryClientFallback implements StockQueryClient {
    @Override
    public List<DetailShoppingCart> queryStock(String cids) {

        System.out.println("order-add中 库存查询 兜底方案");

        return null;
    }
}
