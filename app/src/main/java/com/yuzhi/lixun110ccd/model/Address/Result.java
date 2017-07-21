package com.yuzhi.lixun110ccd.model.Address;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Result {
    private String SellerId;
    private String OrderId;
    private String BuyerId;
    private String ProductId;
    private String RecipientName;
    private String Mobile;
    private String ZipCode;
    private ProvinceItems provinceItems;

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setRecipientName(String recipientName) {
        RecipientName = recipientName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public ProvinceItems getProvinceItems() {
        return provinceItems;
    }

    public void setProvinceItems(ProvinceItems provinceItems) {
        this.provinceItems = provinceItems;
    }
}
