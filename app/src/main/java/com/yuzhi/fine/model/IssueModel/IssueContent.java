package com.yuzhi.fine.model.IssueModel;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/6/15.
 * 发布实体类
 *
 */

public class IssueContent {

    private String Title;
    private String Content;
    private String CategoryID;
    private String Money;
    private String Province;
    private String City;
    private String Address;
    private String PushType;
    private String PushMoney;
    private String TopType;
    private String TopMoney;
    @JSONField(name  = "Title")
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    @JSONField(name  = "Content")
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    @JSONField(name  = "CategoryID")
    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
    @JSONField(name  = "Money")
    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }
    @JSONField(name  = "Province")
    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
    @JSONField(name  = "City")
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
    @JSONField(name  = "Address")
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    @JSONField(name  = "PushType")
    public String getPushType() {
        return PushType;
    }

    public void setPushType(String pushType) {
        PushType = pushType;
    }
    @JSONField(name  = "PushMoney")
    public String getPushMoney() {
        return PushMoney;
    }

    public void setPushMoney(String pushMoney) {
        PushMoney = pushMoney;
    }
    @JSONField(name  = "TopType")
    public String getTopType() {
        return TopType;
    }

    public void setTopType(String topType) {
        TopType = topType;
    }
    @JSONField(name  = "TopMoney")
    public String getTopMoney() {
        return TopMoney;
    }

    public void setTopMoney(String topMoney) {
        TopMoney = topMoney;
    }
}
