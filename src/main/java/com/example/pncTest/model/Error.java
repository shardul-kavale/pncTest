package com.example.pncTest.model;

public class Error {
    public String status;
    public int errorCode;
    public String errorType;
    public String description;

    public Error(String status, int errorCode, String errorType, String description){
        this.errorCode=errorCode;
        this.status=status;
        this.errorType=errorType;
        this.description=description;
    }
}
