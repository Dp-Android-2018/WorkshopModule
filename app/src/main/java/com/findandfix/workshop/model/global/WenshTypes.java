package com.findandfix.workshop.model.global;

import com.google.gson.annotations.SerializedName;

public class WenshTypes {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("w_name")
    private String wName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getwName() {
        return wName;
    }
}
