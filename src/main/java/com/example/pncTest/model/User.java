package com.example.pncTest.model;

public class User {

    public String uuid;
    public String username;
    public String city;

    public User(String uuid, String username, String city){
        this.uuid=uuid;
        this.username=username;
        this.city=city;
    }

    public String getUsername() {
        return  username;
    }
    public String getCity() {
        return  city;
    }
}
