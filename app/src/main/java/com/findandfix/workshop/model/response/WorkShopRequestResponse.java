package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.Links;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.RequestMeta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 27/03/2018.
 */

public class WorkShopRequestResponse {

    @SerializedName("data")
    private List<RequestData> data;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private RequestMeta meta;

    public List<RequestData> getData() {
        return data;
    }

    public Links getLinks() {
        return links;
    }

    public RequestMeta getMeta() {
        return meta;
    }
}
