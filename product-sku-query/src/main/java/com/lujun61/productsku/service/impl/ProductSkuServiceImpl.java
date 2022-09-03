package com.lujun61.productsku.service.impl;

import com.lujun61.beans.entity.ProductSku;
import com.lujun61.productsku.dao.ProductSkuMapper;
import com.lujun61.productsku.service.ProductSkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Override
    public ProductSku queryProductSkuBySkuId(String skuId) {
        return productSkuMapper.selectProductSkuBySkuId(skuId);
    }
}
