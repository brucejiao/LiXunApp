package com.yuzhi.fine.model.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class City {
    private String Name;
    private List<Area> areas;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
