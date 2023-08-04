package com.example.pncTest.model;

import com.example.pncTest.model.enums.Status;

public class ResponseData {

   public Status status;
   public String message;
   public User user;

    public ResponseData(Status status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }


    public String getMessage() {
        return message;
    }
    public User getUser() {
        return user;
    }
    public Status getStatus() {
        return status;
    }
}
