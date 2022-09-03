package com.lujun61.ordersubmit.service.feign.fallback;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.ordersubmit.service.feign.StockUpdateClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockUpdateClientFallback implements StockUpdateClient {
    @Override
    public int updateStock(List<ProductSku> skus) {

        System.out.println("api-order-submit中StockUpdate服务兜底方案");

        return 0;
    }
}
