package com.lujun61.fmmall.dao;

import com.lujun61.beans.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper {

    /**
     * @param productId 逻辑关联的商品id
     * @return java.util.List<com.lujun61.beans.entity.ProductImg>
     * @description 根据商品id查询对应的商品图片信息
     * @author Jun Lu
     * @date 2022-07-30 21:52:53
     */
    List<ProductImg> selectProductImgsByProductId(int productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insert(ProductImg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int insertSelective(ProductImg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    ProductImg selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKeySelective(ProductImg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_img
     *
     * @mbggenerated Fri Jul 29 10:45:02 CST 2022
     */
    int updateByPrimaryKey(ProductImg record);
}