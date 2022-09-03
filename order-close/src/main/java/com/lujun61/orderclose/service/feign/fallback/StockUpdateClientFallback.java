package com.lujun61.orderclose.service.feign.fallback;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.orderclose.service.feign.StockUpdateClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdateClientFallback implements StockUpdateClient {
    @Override
    public int updateStock(List<ProductSku> skus) {

        System.out.println("StockUpdate服务兜底方案~~~~~~");

        return 0;
    }
}
