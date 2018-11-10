package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 17/03/2018.
 */

public class LoginRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("password")
    private String password;

    @SerializedName("device_token")
    private String deviceToken;

    public LoginRequest(String email, String mobile, String password, String deviceToken) {
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.deviceToken = deviceToken;
    }
}
