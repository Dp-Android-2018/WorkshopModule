package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CarOwner implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("mobile")
    private String mobile;

    public int getId() {
        return id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }
}
