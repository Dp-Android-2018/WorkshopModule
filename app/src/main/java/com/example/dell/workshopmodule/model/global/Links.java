package com.example.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 27/03/2018.
 */

public class Links {

    @SerializedName("first")
    private String first;


    @SerializedName("last")
    private String last;

    @SerializedName("prev")
    private String prev;

    @SerializedName("next")
    private String next;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPrev() {
        return prev;
    }

    public String getNext() {
        return next;
    }
}
