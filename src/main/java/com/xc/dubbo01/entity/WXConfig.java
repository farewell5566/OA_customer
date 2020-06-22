package com.xc.dubbo01.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WXConfig {

    @Value(value = "${weixin.appID}")
    String appID;

    @Value(value = "${weixin.appsecret}")
    String appsecret;

    @Value(value = "${weixin.token}")
    String token;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
