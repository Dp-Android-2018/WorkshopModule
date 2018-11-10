package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddAchievmentRequest implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("after_image")
    private String afterImage;

    @SerializedName("before_image")
    private String beforeImage;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }

    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }
}
