package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 27/03/2018.
 */

public class RequestData implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("winch")
    private int winch;

    @SerializedName("notes")
    private String notes;

    @SerializedName("brand")
    private String brand;

    @SerializedName("model")
    private String model;

    @SerializedName("year")
    private String year;

    @SerializedName("address")
    private String address;

    @SerializedName("specializations")
    private List<BaseModel> specializations;

    @SerializedName("images")
    private List<Media> image;

    @SerializedName("videos")
    private List<Media> videos;

    @SerializedName("date")
    private String date;

    @SerializedName("percentage")
    private float percentage;


    @SerializedName("carowner")
    private CarOwner carowner;

    public CarOwner getCarowner() {
        return carowner;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getWinch() {
        return winch;
    }

    public String getNotes() {
        return notes;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getAddress() {
        return address;
    }

    public List<BaseModel> getSpecializations() {
        return specializations;
    }

    public List<Media> getImage() {
        return image;
    }

    public List<Media> getVideos() {
        return videos;
    }

    public String getDate() {
        return date;
    }
}
