package com.lujun61.beans.entity;

import java.util.Date;

public class ProductSku {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.sku_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private String skuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.product_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.sku_name
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private String skuName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.sku_img
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private String skuImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.untitled
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private String untitled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.original_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Double originalPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.sell_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Double sellPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.discounts
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Double discounts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.stock
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Integer stock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.create_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.update_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_sku.status
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.sku_id
     *
     * @return the value of product_sku.sku_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.sku_id
     *
     * @param skuId the value for product_sku.sku_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.product_id
     *
     * @return the value of product_sku.product_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.product_id
     *
     * @param productId the value for product_sku.product_id
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.sku_name
     *
     * @return the value of product_sku.sku_name
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.sku_name
     *
     * @param skuName the value for product_sku.sku_name
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.sku_img
     *
     * @return the value of product_sku.sku_img
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public String getSkuImg() {
        return skuImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.sku_img
     *
     * @param skuImg the value for product_sku.sku_img
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg == null ? null : skuImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.untitled
     *
     * @return the value of product_sku.untitled
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public String getUntitled() {
        return untitled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.untitled
     *
     * @param untitled the value for product_sku.untitled
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setUntitled(String untitled) {
        this.untitled = untitled == null ? null : untitled.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.original_price
     *
     * @return the value of product_sku.original_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Double getOriginalPrice() {
        return originalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.original_price
     *
     * @param originalPrice the value for product_sku.original_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.sell_price
     *
     * @return the value of product_sku.sell_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Double getSellPrice() {
        return sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.sell_price
     *
     * @param sellPrice the value for product_sku.sell_price
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.discounts
     *
     * @return the value of product_sku.discounts
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Double getDiscounts() {
        return discounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.discounts
     *
     * @param discounts the value for product_sku.discounts
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setDiscounts(Double discounts) {
        this.discounts = discounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.stock
     *
     * @return the value of product_sku.stock
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.stock
     *
     * @param stock the value for product_sku.stock
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.create_time
     *
     * @return the value of product_sku.create_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.create_time
     *
     * @param createTime the value for product_sku.create_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.update_time
     *
     * @return the value of product_sku.update_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.update_time
     *
     * @param updateTime the value for product_sku.update_time
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_sku.status
     *
     * @return the value of product_sku.status
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_sku.status
     *
     * @param status the value for product_sku.status
     *
     * @mbggenerated Fri Aug 05 21:13:38 CST 2022
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}