package com.findandfix.workshop.model.global.Conv;

public class WorkshopFire {

    public String device_token;
    public String location;
    public String sender_name;

    public WorkshopFire(){}

    public WorkshopFire(String device_token, String location, String sender_name) {
        this.device_token = device_token;
        this.location = location;
        this.sender_name = sender_name;
    }

}
