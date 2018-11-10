package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class CompleteNotification {

    @SerializedName("key")
    private int key;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("data")
    private CompletePayload data;

    public void setKey(int key) {
        this.key = key;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setData(CompletePayload data) {
        this.data = data;
    }
}
