package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.ProductSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper {

    /**
     * @param productId 商品id
     * @return java.util.List<com.lujun61.beans.entity.ProductSku>
     * @description 根据商品id查询商品套餐中价格最低的套餐
     * @author Jun Lu
     * @date 2022-08-12 08:59:34
     */
    List<ProductSku> selectLowPriceByProductId(String productId);

    /**
     * @param product_id SKU所关联的商品id
     * @param status     商品状态
     * @return java.util.List<com.lujun61.beans.entity.ProductSku>
     * @description 根据商品ID以及商品状态查询商品套餐
     * @author Jun Lu
     * @date 2022-08-01 19:21:54
     */
    List<ProductSku> selectProductSkuByPrimaryKeyAndStatus(@Param("productId") String product_id, @Param("status") int status);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    int deleteByPrimaryKey(String skuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    int insert(ProductSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    int insertSelective(ProductSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    ProductSku selectByPrimaryKey(String skuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    int updateByPrimaryKeySelective(ProductSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_sku
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    int updateByPrimaryKey(ProductSku record);
}