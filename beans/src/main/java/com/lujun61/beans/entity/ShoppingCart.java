package com.lujun61.beans.entity;

import java.math.BigDecimal;

public class ShoppingCart {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.cart_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String cartId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.product_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.sku_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String skuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.user_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.cart_num
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String cartNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.cart_time
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String cartTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.product_price
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private BigDecimal productPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shopping_cart.sku_props
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    private String skuProps;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.cart_id
     *
     * @return the value of shopping_cart.cart_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.cart_id
     *
     * @param cartId the value for shopping_cart.cart_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setCartId(String cartId) {
        this.cartId = cartId == null ? null : cartId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_id
     *
     * @return the value of shopping_cart.product_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_id
     *
     * @param productId the value for shopping_cart.product_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.sku_id
     *
     * @return the value of shopping_cart.sku_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.sku_id
     *
     * @param skuId the value for shopping_cart.sku_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.user_id
     *
     * @return the value of shopping_cart.user_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.user_id
     *
     * @param userId the value for shopping_cart.user_id
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.cart_num
     *
     * @return the value of shopping_cart.cart_num
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getCartNum() {
        return cartNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.cart_num
     *
     * @param cartNum the value for shopping_cart.cart_num
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setCartNum(String cartNum) {
        this.cartNum = cartNum == null ? null : cartNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.cart_time
     *
     * @return the value of shopping_cart.cart_time
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getCartTime() {
        return cartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.cart_time
     *
     * @param cartTime the value for shopping_cart.cart_time
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setCartTime(String cartTime) {
        this.cartTime = cartTime == null ? null : cartTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.product_price
     *
     * @return the value of shopping_cart.product_price
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.product_price
     *
     * @param productPrice the value for shopping_cart.product_price
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shopping_cart.sku_props
     *
     * @return the value of shopping_cart.sku_props
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public String getSkuProps() {
        return skuProps;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shopping_cart.sku_props
     *
     * @param skuProps the value for shopping_cart.sku_props
     *
     * @mbggenerated Thu Aug 04 20:56:05 CST 2022
     */
    public void setSkuProps(String skuProps) {
        this.skuProps = skuProps == null ? null : skuProps.trim();
    }
}