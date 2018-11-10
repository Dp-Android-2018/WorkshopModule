package com.findandfix.workshop.model.request;

import com.findandfix.workshop.model.global.CompleteNotification;
import com.google.gson.annotations.SerializedName;

public class CompleteRequestNotification {

    @SerializedName("notification")
    private CompleteNotification notification;

    public void setNotification(CompleteNotification notification) {
        this.notification = notification;
    }
}
