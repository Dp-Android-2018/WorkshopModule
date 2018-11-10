package com.findandfix.workshop.model.global.Conv;

import java.io.Serializable;

/**
 * Created by DELL on 10/04/2018.
 */

public class User implements Serializable{
    public String email;
    public String name;
    public String profilePicLink;

    public User(){}

    public User(String email, String name, String profilePicLink) {
        this.email = email;
        this.name = name;
        this.profilePicLink = profilePicLink;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }
}
