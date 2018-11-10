package com.findandfix.workshop.model.request;

import com.findandfix.workshop.model.global.Social;
import com.findandfix.workshop.model.global.WorkDayItems;
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

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

    @SerializedName("password")
    private String password;

    @SerializedName("password_confirmation")
    private String passwordConfirmation;

    @SerializedName("website")
    private String website;

    @SerializedName("winch")
    private int winch;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("brands")
    private List<Integer> brands;

    @SerializedName("specializations")
    private List<Integer> specializations;

    @SerializedName("urgent_request_types")
    private List<Integer> urgentTypes;

    public void setWorkdays(List<WorkDayItems> workdays) {
        this.workdays = workdays;
    }

    @SerializedName("workdays")
    private List<WorkDayItems> workdays;

    @SerializedName("papers")
    private List<String> papers;

    @SerializedName("winch_types")
    private List<Integer> winchTypes;

    @SerializedName("social")
    private Social social;

    @SerializedName("profile_images")
    private List<String> workshopProfileImages;

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    public void setWinchTypes(List<Integer> winchTypes) {
        this.winchTypes = winchTypes;
    }

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

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setWinch(int winch) {
        this.winch = winch;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<String> getWorkshopProfileImages() {
        return workshopProfileImages;
    }

    public void setWorkshopProfileImages(List<String> workshopProfileImages) {
        this.workshopProfileImages = workshopProfileImages;
    }
}
