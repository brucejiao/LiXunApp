package com.yuzhi.lixun110ccd.model.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Province {
    private String Name;
    private List<City> cityList;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
