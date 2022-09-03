package com.lujun61.productsku.dao;

import com.lujun61.beans.entity.ProductSku;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSkuMapper {

    ProductSku selectProductSkuBySkuId(String skuId);

}
