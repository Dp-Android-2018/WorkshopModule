package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

public class AchievmentObj {


    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private String createdAt;


    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("after_image")
    private String afterImage;

    @SerializedName("before_image")
    private String beforeImage;

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public String getBeforeImage() {
        return beforeImage;
    }
}
