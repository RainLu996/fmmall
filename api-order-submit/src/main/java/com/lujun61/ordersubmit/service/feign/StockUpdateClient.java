package com.lujun61.ordersubmit.service.feign;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.ordersubmit.service.feign.fallback.StockUpdateClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "stock-update", fallback = StockUpdateClientFallback.class)
public interface StockUpdateClient {

    @PutMapping("/order/stock/update")
    int updateStock(@RequestBody List<ProductSku> skus);

}
