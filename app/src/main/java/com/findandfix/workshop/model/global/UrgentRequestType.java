package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class UrgentRequestType {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
