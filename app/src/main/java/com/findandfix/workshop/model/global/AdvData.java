package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdvData implements Serializable{



    @SerializedName("id")
    private int id;


    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("count_from")
    private int countFrom;

    @SerializedName("count_to")
    private int countTo;

    @SerializedName("image")
    private String image;

    @SerializedName("accepted")
    private boolean accepted;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public int getCountFrom() {
        return countFrom;
    }

    public int getCountTo() {
        return countTo;
    }

    public String getImage() {
        return image;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
