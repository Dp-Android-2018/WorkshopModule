package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.AdvData;
import com.findandfix.workshop.model.global.Links;
import com.findandfix.workshop.model.global.RequestMeta;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OffersResponse {

    @SerializedName("data")
    private List<AdvData> data;


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

    public List<AdvData> getData() {
        return data;
    }
}
