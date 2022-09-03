package com.lujun61.productsku.controller;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.productsku.service.ProductSkuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order/productsku")
public class ProductSkuController {

    @Resource
    private ProductSkuService productSkuService;

    @GetMapping("/query")
    public ProductSku queryProductSku(@RequestParam("skuId") String skuId) {
        return productSkuService.queryProductSkuBySkuId(skuId);
    }

}
