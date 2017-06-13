package com.yuzhi.fine.model.Address;


import java.io.Serializable;

public class AddressModel2 implements Serializable{
    private String Status;
    private String ServerTime;
    private Result result;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String serverTime) {
        ServerTime = serverTime;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
