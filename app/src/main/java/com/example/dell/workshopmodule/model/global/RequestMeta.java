package com.example.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 27/03/2018.
 */

public class RequestMeta {

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("from")
    private int from;

    @SerializedName("last_page")
    private int lastPage;

    @SerializedName("path")
    private String path;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("to")
    private int to;

    @SerializedName("total")
    private int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getFrom() {
        return from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getPath() {
        return path;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }
}
