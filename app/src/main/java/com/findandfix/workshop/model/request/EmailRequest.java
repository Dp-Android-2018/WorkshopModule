package com.findandfix.workshop.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 20/03/2018.
 */

public class EmailRequest {

      @SerializedName("email")
      private String email;

    public EmailRequest(String email) {
        this.email = email;
    }
}
