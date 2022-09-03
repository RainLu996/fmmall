package com.lujun61.ordersubmit.service.feign.fallback;

import com.lujun61.ordersubmit.service.feign.ShopCartDelClient;

public class ShopCartDelClientFallback implements ShopCartDelClient {
    @Override
    public int deleteShopcart(String cids) {

        System.out.println("api-order-submit中ShopCartDel服务兜底方案");

        return 0;
    }
}
