package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

public class MessageData {

    @SerializedName("noti_title")
    private String notificationTitle;

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
}
