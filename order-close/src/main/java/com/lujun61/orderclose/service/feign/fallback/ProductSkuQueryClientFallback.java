package com.lujun61.orderclose.service.feign.fallback;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.orderclose.service.feign.ProductSkuQueryClient;
import org.springframework.stereotype.Component;

@Component
public class ProductSkuQueryClientFallback implements ProductSkuQueryClient {
    @Override
    public ProductSku queryProductSku(String skuId) {

        System.out.println("ProductSkuQuery服务兜底方案~~~~~~");

        return null;
    }
}
