package com.dp.dell.workshopmodule.model.response;

import com.dp.dell.workshopmodule.model.global.AchievmentObj;
import com.dp.dell.workshopmodule.model.global.Links;
import com.dp.dell.workshopmodule.model.global.RequestMeta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AchievmentResponse {

    @SerializedName("data")
    private List<AchievmentObj> data;

    @SerializedName("meta")
    private RequestMeta meta;

    @SerializedName("links")
    private Links links;

    public RequestMeta getMeta() {
        return meta;
    }

    public Links getLinks() {
        return links;
    }

    public List<AchievmentObj> getData() {
        return data;
    }
}
