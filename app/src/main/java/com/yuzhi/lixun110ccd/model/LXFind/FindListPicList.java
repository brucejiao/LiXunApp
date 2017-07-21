package com.yuzhi.lixun110ccd.model.LXFind;

/**
 * Created by Administrator on 2017/6/21.
 * 发布-列表-图片列表
 */

public class FindListPicList {

    private String ID;
    private String PictureID;
    private String PictureType;
    private String PublishID;
    private String IsDelete;
    private String SortID;
    private String ImgFilePath;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String pictureID) {
        PictureID = pictureID;
    }

    public String getPictureType() {
        return PictureType;
    }

    public void setPictureType(String pictureType) {
        PictureType = pictureType;
    }

    public String getPublishID() {
        return PublishID;
    }

    public void setPublishID(String publishID) {
        PublishID = publishID;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    public String getSortID() {
        return SortID;
    }

    public void setSortID(String sortID) {
        SortID = sortID;
    }

    public String getImgFilePath() {
        return ImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        ImgFilePath = imgFilePath;
    }
}
