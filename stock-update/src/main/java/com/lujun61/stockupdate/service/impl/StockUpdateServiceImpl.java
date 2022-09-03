package com.lujun61.stockupdate.service.impl;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.stockupdate.dao.StockUpdateMapper;
import com.lujun61.stockupdate.service.StockUpdateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("stockUpdateService")
public class StockUpdateServiceImpl implements StockUpdateService {

    @Resource
    StockUpdateMapper stockUpdateMapper;

    @Override
    public int updateStock(List<ProductSku> skus) {

        int ret = 1;

        for (ProductSku productSku : skus) {
            int res = stockUpdateMapper.updateStock(productSku);

            ret *= res;
        }

        return ret;
    }
}
