package com.dp.dell.workshopmodule.model.global.Conv;

import java.io.Serializable;

/**
 * Created by DELL on 11/04/2018.
 */

public class Conversations implements Serializable {
    public String conversationUrl;
    public String sender_name;

    public Conversations(String conversationUrl, String sender_name) {
        this.conversationUrl = conversationUrl;
        this.sender_name = sender_name;
    }

    public Conversations(){};
}
