package com.yuzhi.lixun110ccd.model.GoogleLoc2Add;

/**
 * Created by JiaoJianJun on 2017/6/16.
 * 接收谷歌返回的地址信息
 */

public class LocationModels {
    private String road;//路段
    private String county;//县级市
    private String city;//地级市
    private String province;//省
    private String national;//国
    private String detailsAddress;//详细地址

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getDetailsAddress() {
        return detailsAddress;
    }

    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }
}
