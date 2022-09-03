package com.lujun61.ordersubmit.service.feign;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.ordersubmit.service.feign.fallback.OrderAddClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "order-add", fallback = OrderAddClientFallback.class)
public interface OrderAddClient {

    @PostMapping("/order/save")
    List<DetailShoppingCart> orderAdd(@RequestBody Orders order, @RequestParam("cids") String cids);

}
