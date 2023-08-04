package com.example.pncTest.model;

import com.example.pncTest.model.enums.ErrorCode;
import com.example.pncTest.model.enums.ErrorType;
import com.example.pncTest.model.enums.Status;

import java.util.List;

public class Error {
    public Status status;
    public int errorCode;
    public ErrorType errorType;
    public List<String> description;


    //single error constructor
    public Error(Status status, int errorCode, ErrorType errorType, String description){
        this.errorCode=errorCode;
        this.status=status;
        this.errorType=errorType;
        this.description= List.of(description);
    }

    //multiple error constructor. (for multiple validation errors)
    public Error(Status status, int errorCode, ErrorType errorType, List<String> description) {
        this.errorCode=errorCode;
        this.status=status;
        this.errorType=errorType;
        this.description = description;
    }
}
