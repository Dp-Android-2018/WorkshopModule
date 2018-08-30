package com.dp.dell.workshopmodule.model.request;

import com.dp.dell.workshopmodule.model.global.CompleteNotification;
import com.google.gson.annotations.SerializedName;

public class CompleteRequestNotification {

    @SerializedName("notification")
    private CompleteNotification notification;

    public void setNotification(CompleteNotification notification) {
        this.notification = notification;
    }
}
