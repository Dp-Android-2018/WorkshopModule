package com.dp.dell.workshopmodule.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 13/05/2018.
 */

public class ChangePasswordRequest {

    @SerializedName("password")
    private String password;

    @SerializedName("password_confirmation")
    private String passwordConfirmation;


    @SerializedName("current_password")
    private String currentPassword;

    public ChangePasswordRequest(String password, String passwordConfirmation, String currentPassword) {
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.currentPassword = currentPassword;
    }
}
