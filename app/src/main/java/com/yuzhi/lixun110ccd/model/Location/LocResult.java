package com.yuzhi.lixun110ccd.model.Location;

/**
 * Created by JiaoJianJun on 2017/6/15.
 */

public class LocResult {
    private LocLocation location;
    private String formatted_address;
    private String business;
    private LocAddressCom addressComponent;
    private String cityCode;

    public LocLocation getLocation() {
        return location;
    }

    public void setLocation(LocLocation location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public LocAddressCom getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(LocAddressCom addressComponent) {
        this.addressComponent = addressComponent;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
