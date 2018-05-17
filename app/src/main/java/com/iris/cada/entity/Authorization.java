package com.iris.cada.entity;

/**
 * Created by iris on 2018/4/2.
 */

public class Authorization {
    private String type;
    private String authorization;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "type='" + type + '\'' +
                ", authorization='" + authorization + '\'' +
                '}';
    }
}
