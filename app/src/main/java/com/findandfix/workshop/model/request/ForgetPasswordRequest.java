package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordRequest {


    @SerializedName("confirmation_code")
    private String confirmationCode;

    @SerializedName("password")
    private String password;

    public ForgetPasswordRequest(String confirmationCode, String password) {
        this.confirmationCode = confirmationCode;
        this.password = password;
    }
}
