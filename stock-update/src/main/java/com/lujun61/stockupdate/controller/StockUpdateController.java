package com.lujun61.stockupdate.controller;


import com.lujun61.beans.entity.ProductSku;
import com.lujun61.stockupdate.service.StockUpdateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order/stock")
public class StockUpdateController {

    @Resource
    StockUpdateService stockUpdateService;

    @PutMapping("/update")
    public int updateStock(@RequestBody List<ProductSku> skus) {
        return stockUpdateService.updateStock(skus);
    }

}
