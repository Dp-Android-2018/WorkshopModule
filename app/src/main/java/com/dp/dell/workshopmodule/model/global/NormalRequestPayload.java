package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 07/05/2018.
 */

public class NormalRequestPayload {
    @SerializedName("request_id")
    private int requestId;

    public NormalRequestPayload(int requestId) {
        this.requestId = requestId;
    }
}
