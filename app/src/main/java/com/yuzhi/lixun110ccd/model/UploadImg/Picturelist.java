package com.yuzhi.lixun110ccd.model.UploadImg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/6/15.
 * 发布图片List对象
 */

public class Picturelist {
    private String PictureID;
    @JSONField(name  = "PictureID")
    public String getPictureID() {
        return PictureID;
    }

    public void setPictureID(String pictureID) {
        PictureID = pictureID;
    }
}
