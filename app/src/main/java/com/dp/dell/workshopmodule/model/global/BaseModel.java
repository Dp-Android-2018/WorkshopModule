package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Nullable;

/**
 * Created by DELL on 18/03/2018.
 */

public class BaseModel implements Serializable{
    public BaseModel() {

    }

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
