package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UrgentRequestData {

    @SerializedName("id")
    private int id;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

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

    @SerializedName("type")
    private UrgentRequestType type;

    @SerializedName("date")
    private String date;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("winch_types")
    private List<WenshTypes> winchType;


    @SerializedName("voice_notes")
    private String voiceNotes;

    @SerializedName("distance")
    private String distance;


    @SerializedName("carowner")
    private CarOwner carowner;


    public int getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
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


    public String getDistance() {
        return distance;
    }

    public UrgentRequestType getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getMobile() {
        return mobile;
    }

    public List<WenshTypes> getWinchType() {
        return winchType;
    }

    public String getVoiceNotes() {
        return voiceNotes;
    }

    public CarOwner getCarowner() {
        return carowner;
    }
}
