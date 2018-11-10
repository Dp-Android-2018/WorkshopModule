package com.findandfix.workshop.model.response;

import com.google.gson.annotations.SerializedName;

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
