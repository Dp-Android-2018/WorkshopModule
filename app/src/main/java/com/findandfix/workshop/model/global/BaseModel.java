package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
