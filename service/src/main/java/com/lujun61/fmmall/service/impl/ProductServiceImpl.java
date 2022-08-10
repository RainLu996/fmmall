package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.*;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.*;
import com.lujun61.fmmall.service.ProductService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Resource
    ProductImgMapper productImgMapper;

    @Resource
    ProductSkuMapper productSkuMapper;

    @Resource
    ProductParamsMapper productParamsMapper;

    @Resource
    ProductCommentsMapper productCommentsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryRecommendProducts() {
        List<ProductDetail> productDetails = productMapper.selectRecommendProducts();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", productDetails);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryProductBaseInfoById(String productId) {
        Product product = productMapper.selectProductByPrimaryKeyAndStatus(productId, 1);

        if (product != null) {
            List<ProductImg> productImgs = productImgMapper.selectProductImgsByProductId(productId);

            List<ProductSku> productSkus = productSkuMapper.selectProductSkuByPrimaryKeyAndStatus(productId, 1);

            HashMap<String, Object> baseProductInfo = new HashMap<>();
            baseProductInfo.put("product", product);
            baseProductInfo.put("productImgs", productImgs);
            baseProductInfo.put("productSkus", productSkus);

            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", baseProductInfo);
        }

        return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "所查询的商品不存在", null);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryProductParamsById(String productId) {
        ProductParams productParams = productParamsMapper.selectProductParamsByProductId(productId);

        if (productParams != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", productParams);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "三无产品", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryDetailProductCommentsByProductId(String productId) {
        List<DetailProductComments> detailProductComments = productCommentsMapper.selectDetailProductCommentsByProductId(productId);



        if (detailProductComments != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", detailProductComments);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo pageQueryDetailProductCommentsByProductId(String productId, int pageNum, int pageSize) {

        int count = productCommentsMapper.selectProductCommentCountByProductId(productId);

        int start = (pageNum - 1) * pageSize;

        int pageCount = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;

        List<DetailProductComments> detailProductComments = productCommentsMapper.pageSelectDetailProductCommentsByProductId(productId, start, pageSize);

        if (detailProductComments != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", new PageHelper<>(pageCount, count, detailProductComments));
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getMultiClassificationCommentsCountByProductId(String productId) {

        int count = productCommentsMapper.selectProductCommentCountByProductId(productId);

        if (count != 0) {
            int niceCount = productCommentsMapper.selectCountForNiceCommentByProductId(productId);

            int midCount = productCommentsMapper.selectCountForMidCommentByProductId(productId);

            int badCount = count - niceCount - midCount;

            double favorableRate = ((double) niceCount / count) * 100;

            String favorableRateStr = String.format("%.2f", favorableRate);

            Map<String, Object> commentsCountMap = new HashMap<>();
            commentsCountMap.put("count", count);
            commentsCountMap.put("niceCount", niceCount);
            commentsCountMap.put("midCount", midCount);
            commentsCountMap.put("badCount", badCount);
            commentsCountMap.put("favorableRate", favorableRateStr);

            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", commentsCountMap);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }
}
