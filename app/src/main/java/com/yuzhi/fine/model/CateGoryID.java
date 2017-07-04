package com.yuzhi.fine.model;

/**
 * Created by JiaoJianJun on 2017/7/4.
 * 一二级菜单的id
 */

public class CateGoryID {
    private String CategoryID;
    private String CategoryTitle;
    private String ParentID;
    private String CategoryList;
    private String CategoryLayer;
    private String SortID;
    private String LinkUrl;
    private String PictureID;
    private String Content;
    private String IsDelete;
    private String CreateTime;
    private String UpdateTime;
    private String IsLeaf;
    private String CallIndex;
    private String PictureFilePath;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public String getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(String categoryList) {
        CategoryList = categoryList;
    }

    public String getCategoryLayer() {
        return CategoryLayer;
    }

    public void setCategoryLayer(String categoryLayer) {
        CategoryLayer = categoryLayer;
    }

    public String getSortID() {
        return SortID;
    }

    public void setSortID(String sortID) {
        SortID = sortID;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String pictureID) {
        PictureID = pictureID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
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

    public String getIsLeaf() {
        return IsLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        IsLeaf = isLeaf;
    }

    public String getCallIndex() {
        return CallIndex;
    }

    public void setCallIndex(String callIndex) {
        CallIndex = callIndex;
    }

    public String getPictureFilePath() {
        return PictureFilePath;
    }

    public void setPictureFilePath(String pictureFilePath) {
        PictureFilePath = pictureFilePath;
    }
}
