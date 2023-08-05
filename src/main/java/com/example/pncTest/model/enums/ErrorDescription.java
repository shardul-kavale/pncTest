package com.example.pncTest.model.enums;

public enum ErrorDescription {

    MISSING_USERNAME("The username cannot be empty"),
    MISSING_PASSWORD("The password cannot be empty"),
    MISSING_IP("The ipAddress cannot be empty"),
    NULL_USERNAME("The username cannot be null"),
    NULL_PASSWORD("The password cannot be null"),
    NULL_IP("The ipAddress cannot be null"),
    INELIGIBLE_ERROR("Sorry, only users in Canada are eligible to register!"),
    INTERNAL_SERVER_ERROR("Something's wrong on our end. Please try again later"),
    INVALID_IP("Please enter a valid IP Address");


    private final String description;

    ErrorDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
