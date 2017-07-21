package com.yuzhi.lixun110ccd.model.UserInfos;

/**
 * Created by Administrator on 2017/7/13.
 * 消息列表
 */

public class MessageList {
    private  String ID;
    private  String UserType;
    private  String UserID;
    private  String MessageCategoryID;
    private  String MessageSubject;
    private  String MessageBody;
    private  String MessageTime;
    private  String PictureID;
    private  String LinkUrl;
    private  String IsDelete;
    private  String IsRead;
    private  String CategoryName;
    private  String PicturePath;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getMessageCategoryID() {
        return MessageCategoryID;
    }

    public void setMessageCategoryID(String messageCategoryID) {
        MessageCategoryID = messageCategoryID;
    }

    public String getMessageSubject() {
        return MessageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        MessageSubject = messageSubject;
    }

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(String messageBody) {
        MessageBody = messageBody;
    }

    public String getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(String messageTime) {
        MessageTime = messageTime;
    }

    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String pictureID) {
        PictureID = pictureID;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }
}
