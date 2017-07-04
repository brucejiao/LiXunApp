package com.yuzhi.fine.model.LXFind;

/**
 * Created by Administrator on 2017/6/21.
 * 发现--列表
 */

public class FindListBean {
    private String PublishID;//发布ID
    private String Title;// 标题
    private String Content;// 内容
    private String UserID;
    private String PictureID;// 图片
    private String CategoryID;
    private String Money;
    private String Province;
    private String City;
    private String Country;
    private String Address;
    private String PushType;//推广 1-全国推广  0-不推广
    private String PushMoney;
    private String TopType;
    private String TopMoney;
    private String CreateTime;
    private String UpdateTime;// 更新时间
    private String PublishStatus;// 发布状态（1.待发布，2.已发布，3.已结束，4.已完成）
    private String IsDelete;// 删除状态1:已删除
    private String CheckState;// 审核状态(1.待审核，2.审核通过，3.审核不通过)
    private String CheckID;// 审核人ID
    private String CheckTime;// 审核时间
    private String CheckRemark;// 审核备注
    private String FollowCount;// 关注人数
    private String CommentCount;// 评论人数
    private String VisitCount;//// 浏览次数
    private String ClueUserID;// 提供线索用户ID
    private String ClueUserName;// 提供线索用户名
    private String PaymentTypeID;// 支付方式
    private String PaymentTypeName;// 支付名称
    private String PaymentStatus;// 付款状态---1未支付 、 2支付定金、3支付完成
    private String DatePayOrder;// 付款时间
    private String MoneyPaid;// 实付金额
    private String UserName;// 用户名
    private String CheckUserName;// 审核人用户名
    private String ImgFilePath;
    private String ProvinceName;
    private String CityName;
    private String CountryName;
    private String PictureList;
    private String PicturePath;
    private String CategoryName;///12
    private String FollowTime;// 关注（收藏）、浏览时间——查询收藏、浏览发布信息时使用
    private String PublishAddress;
    private String ParentCategoryID;
    private String ParentCategoryName;

    public String getPublishID() {
        return PublishID;
    }

    public void setPublishID(String publishID) {
        PublishID = publishID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String pictureID) {
        PictureID = pictureID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPushType() {
        return PushType;
    }

    public void setPushType(String pushType) {
        PushType = pushType;
    }

    public String getPushMoney() {
        return PushMoney;
    }

    public void setPushMoney(String pushMoney) {
        PushMoney = pushMoney;
    }

    public String getTopType() {
        return TopType;
    }

    public void setTopType(String topType) {
        TopType = topType;
    }

    public String getTopMoney() {
        return TopMoney;
    }

    public void setTopMoney(String topMoney) {
        TopMoney = topMoney;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getPublishStatus() {
        return PublishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        PublishStatus = publishStatus;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    public String getCheckState() {
        return CheckState;
    }

    public void setCheckState(String checkState) {
        CheckState = checkState;
    }

    public String getCheckID() {
        return CheckID;
    }

    public void setCheckID(String checkID) {
        CheckID = checkID;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String checkTime) {
        CheckTime = checkTime;
    }

    public String getCheckRemark() {
        return CheckRemark;
    }

    public void setCheckRemark(String checkRemark) {
        CheckRemark = checkRemark;
    }

    public String getFollowCount() {
        return FollowCount;
    }

    public void setFollowCount(String followCount) {
        FollowCount = followCount;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(String commentCount) {
        CommentCount = commentCount;
    }

    public String getVisitCount() {
        return VisitCount;
    }

    public void setVisitCount(String visitCount) {
        VisitCount = visitCount;
    }

    public String getClueUserID() {
        return ClueUserID;
    }

    public void setClueUserID(String clueUserID) {
        ClueUserID = clueUserID;
    }

    public String getClueUserName() {
        return ClueUserName;
    }

    public void setClueUserName(String clueUserName) {
        ClueUserName = clueUserName;
    }

    public String getPaymentTypeID() {
        return PaymentTypeID;
    }

    public void setPaymentTypeID(String paymentTypeID) {
        PaymentTypeID = paymentTypeID;
    }

    public String getPaymentTypeName() {
        return PaymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        PaymentTypeName = paymentTypeName;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getDatePayOrder() {
        return DatePayOrder;
    }

    public void setDatePayOrder(String datePayOrder) {
        DatePayOrder = datePayOrder;
    }

    public String getMoneyPaid() {
        return MoneyPaid;
    }

    public void setMoneyPaid(String moneyPaid) {
        MoneyPaid = moneyPaid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCheckUserName() {
        return CheckUserName;
    }

    public void setCheckUserName(String checkUserName) {
        CheckUserName = checkUserName;
    }

    public String getImgFilePath() {
        return ImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        ImgFilePath = imgFilePath;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getPictureList() {
        return PictureList;
    }

    public void setPictureList(String pictureList) {
        PictureList = pictureList;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getFollowTime() {
        return FollowTime;
    }

    public void setFollowTime(String followTime) {
        FollowTime = followTime;
    }

    public String getPublishAddress() {
        return PublishAddress;
    }

    public void setPublishAddress(String publishAddress) {
        PublishAddress = publishAddress;
    }

    public String getParentCategoryID() {
        return ParentCategoryID;
    }

    public void setParentCategoryID(String parentCategoryID) {
        ParentCategoryID = parentCategoryID;
    }

    public String getParentCategoryName() {
        return ParentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        ParentCategoryName = parentCategoryName;
    }
}
