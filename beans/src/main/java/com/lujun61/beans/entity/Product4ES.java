package com.lujun61.beans.entity;

public class Product4ES {

    private String productId;
    private String productName;
    private String productImg;
    private String productSkuName;
    private Integer soldNum;
    private Double proSkuPrice;

    public Product4ES() {
    }

    public Product4ES(String productId, String productName, String productImg, String productSkuName, Integer soldNum, Double proSkuPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productSkuName = productSkuName;
        this.soldNum = soldNum;
        this.proSkuPrice = proSkuPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Double getProSkuPrice() {
        return proSkuPrice;
    }

    public void setProSkuPrice(Double proSkuPrice) {
        this.proSkuPrice = proSkuPrice;
    }

    @Override
    public String toString() {
        return "Product4ES{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productSkuName='" + productSkuName + '\'' +
                ", soldNum=" + soldNum +
                ", proSkuPrice=" + proSkuPrice +
                '}';
    }
}
