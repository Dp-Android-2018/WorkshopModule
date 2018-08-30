package com.dp.dell.workshopmodule.model.response;

import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.WenshTypes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WinshResponse {

    @SerializedName("data")
    private List<WenshTypes> data;

    public List<WenshTypes> getData() {
        return data;
    }
}
