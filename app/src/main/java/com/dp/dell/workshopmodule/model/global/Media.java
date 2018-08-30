package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DELL on 27/03/2018.
 */

public class Media implements Serializable{
    @SerializedName("path")
    private String path;

    public String getPath() {
        return path;
    }
}
