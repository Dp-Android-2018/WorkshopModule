package com.dp.dell.workshopmodule.model.request;

import com.dp.dell.workshopmodule.model.global.WenshTypes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 21/04/2018.
 */

public class UpdateProfileRequest {

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

    @SerializedName("winch_types")
    private List<Integer> winchTypes;

    @SerializedName("website")
    private String website;

    @SerializedName("brands")
    private List<Integer> brands;

    @SerializedName("specializations")
    private List<Integer> specializations;

    @SerializedName("urgent_request_types")
    private List<Integer> urgentTypes;

    public void setWinchTypes(List<Integer> winchTypes) {
        this.winchTypes = winchTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
}
