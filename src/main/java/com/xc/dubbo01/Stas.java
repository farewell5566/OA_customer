package com.xc.dubbo01;


public class Stas {

    private int statusCode;
    private String mess;
    private String data;

    public Stas() {
    }

    public Stas(int statusCode, String mess, String data) {
        this.statusCode = statusCode;
        this.mess = mess;
        this.data = data;
    }

    static public Stas build(int code) {
        return new Stas(code, "right", "ok");
    }

    static public Stas error(String mess) {
        return new Stas(400, "error", mess);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
