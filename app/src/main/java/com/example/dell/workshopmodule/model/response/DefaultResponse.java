package com.example.dell.workshopmodule.model.response;

import com.example.dell.workshopmodule.model.global.CountryItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 28/03/2018.
 */

public class DefaultResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
