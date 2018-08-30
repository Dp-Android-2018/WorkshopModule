package com.dp.dell.workshopmodule.model.request;

import com.dp.dell.workshopmodule.model.global.Notification;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 28/03/2018.
 */

public class AddOfferRequest {

    @SerializedName("description")
    private String description;

    @SerializedName("days_from")
    private int daysFrom;

    @SerializedName("days_to")
    private int daysTo;

    @SerializedName("price_from")
    private int priceFrom;

    @SerializedName("price_to")
    private int priceTo;

    @SerializedName("notification")
    private Notification notification;

    public AddOfferRequest(String description, int daysFrom, int daysTo, int priceFrom, int priceTo,Notification notification) {
        this.description = description;
        this.daysFrom = daysFrom;
        this.daysTo = daysTo;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.notification=notification;
    }
}
