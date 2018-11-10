package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class NotificationExp {

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public NotificationExp(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
