package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DELL on 30/07/2018.
 */

public class UrgentModel extends BaseModel implements Serializable {



    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }
}
