package com.example.pncTest.model;

import java.util.Optional;

public class ResponseData {

   public String status;
   public String message;

   public User user;

    public ResponseData(String status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }


}
