package com.lujun61.stockquery.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.stockquery.dao.StockQueryMapper;
import com.lujun61.stockquery.service.StockQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service("stockQueryService")
public class StockQueryServiceImpl implements StockQueryService {

    @Resource
    StockQueryMapper stockQueryMapper;

    @Override
    public List<DetailShoppingCart> queryShopcartByCartIds(String cIdstr) {

        String[] cartIds = cIdstr.split(",");

        return stockQueryMapper.selectShopcartByCartIds(Arrays.asList(cartIds));
    }
}
