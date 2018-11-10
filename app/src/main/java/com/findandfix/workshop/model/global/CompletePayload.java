package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class CompletePayload {
    @SerializedName("noti_title")
    private String notificationTitle;

    @SerializedName("workshop_id")
    private String workshopId;

    @SerializedName("key")
    private int key;

    @SerializedName("workshop_name")
    private String workShopName;

    public void setWorkShopName(String workShopName) {
        this.workShopName = workShopName;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }
}
