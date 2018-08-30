package com.dp.dell.workshopmodule.model.global;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 28/07/2018.
 */

public class Social implements Serializable {

    @SerializedName("facebook")
    private String facebook;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("instagram")
    private String instagram;

    @SerializedName("youtube")
    private String youtube;

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
