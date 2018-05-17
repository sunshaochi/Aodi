package com.iris.cada.entity;


/**
 * Created by iris on 2018/1/26.
 */

public class UserInfoBean {
    private LoginBackInfo userWeb;
    private Authorization userCarPlatform;

    public LoginBackInfo getUserWeb() {
        return userWeb;
    }

    public void setUserWeb(LoginBackInfo userWeb) {
        this.userWeb = userWeb;
    }

    public Authorization getUserCarPlatform() {
        return userCarPlatform;
    }

    public void setUserCarPlatform(Authorization userCarPlatform) {
        this.userCarPlatform = userCarPlatform;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userWeb=" + userWeb +
                ", userCarPlatform=" + userCarPlatform +
                '}';
    }
}
