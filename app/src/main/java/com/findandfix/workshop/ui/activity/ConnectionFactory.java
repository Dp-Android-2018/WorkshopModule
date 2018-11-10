package com.findandfix.workshop.ui.activity;

/**
 * Created by DELL on 03/06/2018.
 */

public abstract class ConnectionFactory {
    private String channelName;
    private String socketId;

    public ConnectionFactory() {
    }

    public abstract String getBody();

    public abstract String getCharset();

    public abstract String getContentType();

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}
