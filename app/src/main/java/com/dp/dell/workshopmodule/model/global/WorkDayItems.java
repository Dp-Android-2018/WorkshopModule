package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DELL on 12/08/2018.
 */

public class WorkDayItems implements Serializable{

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("day")
    private String day;

    @SerializedName("shift")
    private String shift;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
