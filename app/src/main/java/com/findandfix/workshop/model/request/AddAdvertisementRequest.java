package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddAdvertisementRequest implements Serializable{

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("count_from")
    private int countFrom;

    @SerializedName("count_to")
    private int countTo;

    @SerializedName("image")
    private String image;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCountFrom(int countFrom) {
        this.countFrom = countFrom;
    }

    public void setCountTo(int countTo) {
        this.countTo = countTo;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
