package com.dp.dell.workshopmodule.model.global.Conv;

/**
 * Created by DELL on 11/04/2018.
 */

public class Message {
    public String content;
    public String fromID;
    public String toID;
    public boolean isRead;
    public String type;
    public Long timestamp;

    public Message(){};
    public Message(String content, String fromID, String toID, boolean isRead, String type, Long timestamp ) {
        this.content = content;
        this.fromID = fromID;
        this.toID = toID;
        this.isRead = isRead;
        this.type = type;
        this.timestamp = timestamp;
    }
}
