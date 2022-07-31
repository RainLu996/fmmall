package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.ProductDetail;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.ProductMapper;
import com.lujun61.fmmall.service.ProductService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public ResultVo queryRecommendProducts() {
        List<ProductDetail> productDetails = productMapper.selectRecommendProducts();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", productDetails);
    }
}
