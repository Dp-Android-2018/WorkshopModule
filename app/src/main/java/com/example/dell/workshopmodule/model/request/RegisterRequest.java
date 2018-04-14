package com.example.dell.workshopmodule.model.request;

import com.example.dell.workshopmodule.model.global.WorkdaysItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 21/03/2018.
 */

public class RegisterRequest implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("password")
    private String password;

    @SerializedName("password_confirmation")
    private String passwordConfirmation;

    @SerializedName("website")
    private String website;


    @SerializedName("brands")
    private List<Integer> brands;

    @SerializedName("specializations")
    private List<Integer> specializations;

    @SerializedName("urgent_request_types")
    private List<Integer> urgentTypes;

    public void setWorkdays(List<WorkdaysItem> workdays) {
        this.workdays = workdays;
    }

    @SerializedName("workdays")
    private List<WorkdaysItem> workdays;

    @SerializedName("papers")
    private List<String> papers;

    public void setPapers(List<String> papers) {
        this.papers = papers;
    }

    public List<Integer> getBrands() {
        return brands;
    }

    public void setBrands(List<Integer> brands) {
        this.brands = brands;
    }

    public List<Integer> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Integer> specializations) {
        this.specializations = specializations;
    }

    public List<Integer> getUrgentTypes() {
        return urgentTypes;
    }

    public void setUrgentTypes(List<Integer> urgentTypes) {
        this.urgentTypes = urgentTypes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
