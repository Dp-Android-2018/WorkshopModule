package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class MessageNotification {
    @SerializedName("key")
    private int key;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("data")
    private MessageData data;

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

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }
}
