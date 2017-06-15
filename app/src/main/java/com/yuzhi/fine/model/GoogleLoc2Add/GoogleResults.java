package com.yuzhi.fine.model.GoogleLoc2Add;

/**
 * Created by Administrator on 2017/6/16.
 */

public class GoogleResults {
    private String address_components;
    private String formatted_address;
    private String geometry;
    private String place_id;
    private String types;

    public String getAddress_components() {
        return address_components;
    }

    public void setAddress_components(String address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
