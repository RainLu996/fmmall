package com.lujun61.fmmall.service;

import com.lujun61.fmmall.vo.ResultVo;

public interface ProductService {

    ResultVo queryRecommendProducts();

    ResultVo queryProductBaseInfoById(String productId);

    ResultVo queryProductParamsById(String productId);

    ResultVo queryDetailProductCommentsByProductId(String productId);

    ResultVo pageQueryDetailProductCommentsByProductId(String productId, int pageNum, int pageSize);

    ResultVo getMultiClassificationCommentsCountByProductId(String productId);

    ResultVo pageQueryDetailProductByCategoryId(String categoryId, int pageNum, int pageSize);

    ResultVo queryBrandByCategoryId(String categoryId);

    ResultVo pageVagueQueryDetailProduct(String keyword, int pageNum, int pageSize);

    ResultVo vagueQueryBrand(String keyword);
}
