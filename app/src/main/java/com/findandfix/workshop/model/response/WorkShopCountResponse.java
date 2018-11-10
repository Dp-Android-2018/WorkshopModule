package com.findandfix.workshop.model.response;

import com.google.gson.annotations.SerializedName;

public class WorkShopCountResponse {

    @SerializedName("normal_requests")
    private int normalRequestsCount;

    @SerializedName("urgent_requests")
    private int urgentRequestsCount;


    @SerializedName("advertising")
    private int advsCount;

    public int getNormalRequestsCount() {
        return normalRequestsCount;
    }

    public int getUrgentRequestsCount() {
        return urgentRequestsCount;
    }

    public int getAdvsCount() {
        return advsCount;
    }
}
