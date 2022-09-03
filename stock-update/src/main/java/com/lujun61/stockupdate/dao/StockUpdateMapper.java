package com.lujun61.stockupdate.dao;

import com.lujun61.beans.entity.ProductSku;
import org.springframework.stereotype.Repository;

@Repository
public interface StockUpdateMapper {

    int updateStock(ProductSku sku);

}
