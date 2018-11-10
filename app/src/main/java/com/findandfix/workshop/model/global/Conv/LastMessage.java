package com.findandfix.workshop.model.global.Conv;

import java.io.Serializable;

/**
 * Created by DELL on 09/05/2018.
 */

public class LastMessage implements Serializable{

    public String content;
    public Long timestamp;

    public LastMessage(){};
    public LastMessage(String content, Long timestampNow ) {
        this.content = content;
        this.timestamp = timestampNow;
    }

    public String getContent() {
        return content;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
