package com.dp.dell.workshopmodule.model.request;

import com.google.gson.annotations.SerializedName;

public class GetMobileCode {

    @SerializedName("mobile")
    private String mobile;

    public GetMobileCode(String mobile) {
        this.mobile = mobile;
    }
}
