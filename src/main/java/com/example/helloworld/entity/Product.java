package com.example.helloworld.entity;

/**
 * @author guantong
 * date: 2023.07.20
 */
public class Product {
    /**
     * 产品主键
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品价格
     */
    private Integer productPrice;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 产品图例
     */
    private String productImg;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productType='" + productType + '\'' +
                ", productImg='" + productImg + '\'' +
                '}';
    }
}
