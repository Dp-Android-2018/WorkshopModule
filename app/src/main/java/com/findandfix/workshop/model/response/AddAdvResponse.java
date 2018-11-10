package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.AdvData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAdvResponse {
    @SerializedName("data")
    private List<AdvData> data;

    public List<AdvData> getData() {
        return data;
    }
}
