package com.dp.dell.workshopmodule.model.request;

import com.google.gson.annotations.SerializedName;

public class CheckCode {

    @SerializedName("confirmation_code")
    private String confirmationCode;

    public CheckCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
