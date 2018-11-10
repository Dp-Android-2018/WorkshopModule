package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.Links;
import com.findandfix.workshop.model.global.RequestMeta;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkShopUrgentRequestResponse {

    @SerializedName("data")
    private List<UrgentRequestData> data;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private RequestMeta meta;

    public List<UrgentRequestData> getData() {
        return data;
    }

    public Links getLinks() {
        return links;
    }

    public RequestMeta getMeta() {
        return meta;
    }
}
