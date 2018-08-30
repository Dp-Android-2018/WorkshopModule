package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

public class MsgRequest {

    @SerializedName("notification")
    private MessageNotification notification;

    public MessageNotification getNotification() {
        return notification;
    }

    public void setNotification(MessageNotification notification) {
        this.notification = notification;
    }
}
