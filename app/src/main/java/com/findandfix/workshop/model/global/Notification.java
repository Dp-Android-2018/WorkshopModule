package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 07/05/2018.
 */

public class Notification {

    @SerializedName("key")
    private int key;

    @SerializedName("device_token")
    private String deviceToken;


    @SerializedName("data")
    private Payload data;



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Payload getData() {
        return data;
    }

    public void setData(Payload data) {
        this.data = data;
    }
}
