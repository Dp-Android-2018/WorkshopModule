package com.example.dell.workshopmodule.model.request;

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

    public AddOfferRequest(String description, int daysFrom, int daysTo, int priceFrom, int priceTo) {
        this.description = description;
        this.daysFrom = daysFrom;
        this.daysTo = daysTo;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }
}
