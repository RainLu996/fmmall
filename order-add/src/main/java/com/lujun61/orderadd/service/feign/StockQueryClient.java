package com.lujun61.orderadd.service.feign;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.orderadd.service.feign.fallback.StockQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "stock-query", fallback = StockQueryClientFallback.class)
public interface StockQueryClient {

    @GetMapping("/order/stock/query")
    List<DetailShoppingCart> queryStock(@RequestParam("cids") String cids);

}
