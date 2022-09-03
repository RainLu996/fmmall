package com.lujun61.orderclose.service.feign;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.orderclose.service.feign.fallback.ProductSkuQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-sku-query", fallback = ProductSkuQueryClientFallback.class)
public interface ProductSkuQueryClient {

    @GetMapping("/order/productsku/query")
    ProductSku queryProductSku(@RequestParam("skuId") String skuId);

}
