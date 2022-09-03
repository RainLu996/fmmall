package com.lujun61.stockupdate.service;

import com.lujun61.beans.entity.ProductSku;

import java.util.List;

public interface StockUpdateService {

    int updateStock(List<ProductSku> skus);

}
