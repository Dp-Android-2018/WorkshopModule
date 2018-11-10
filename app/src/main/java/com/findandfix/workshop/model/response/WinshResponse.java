package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.WenshTypes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WinshResponse {

    @SerializedName("data")
    private List<WenshTypes> data;

    public List<WenshTypes> getData() {
        return data;
    }
}
