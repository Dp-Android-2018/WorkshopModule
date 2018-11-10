package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 03/04/2018.
 */

public class MobileRequest {

    @SerializedName("mobile")
    private String mobile;

    public MobileRequest(String mobile) {
        this.mobile = mobile;
    }
}
