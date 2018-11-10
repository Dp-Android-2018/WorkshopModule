package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class Payload {

    @SerializedName("noti_title")
    private String notificationTitle;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("key")
    private int key;

    public void setKey(int key) {
        this.key = key;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
