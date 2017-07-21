package com.yuzhi.lixun110ccd.http;

/**
 * Created by jjj
 * 返回
 */

public class RestApiResponse {

    private String Result;
    private String Code;
    private String Message;
    private String Data;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }



}
