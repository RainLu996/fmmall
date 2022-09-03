package com.lujun61.ordersubmit.service.feign;

import com.lujun61.ordersubmit.service.feign.fallback.ShopCartDelClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shopcart-del", fallback = ShopCartDelClientFallback.class)
public interface ShopCartDelClient {

    @DeleteMapping("/order/stock/delete")
    int deleteShopcart(@RequestParam("cids") String cids);

}
