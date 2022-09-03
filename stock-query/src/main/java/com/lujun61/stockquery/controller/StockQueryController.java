package com.lujun61.stockquery.controller;


import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.stockquery.service.StockQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order/stock")
public class StockQueryController {

    @Resource
    StockQueryService stockQueryService;

    @GetMapping("/query")
    public List<DetailShoppingCart> queryStock(@RequestParam("cids") String cids) {
        return stockQueryService.queryShopcartByCartIds(cids);
    }

}
