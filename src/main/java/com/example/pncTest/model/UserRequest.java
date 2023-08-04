package com.example.pncTest.model;
import jakarta.validation.constraints.*;
public class UserRequest {
    @NotNull(message = "The username cannot be null.")
    @NotEmpty(message = "The username cannot be empty.")
    public String username;

    @NotNull(message = "The password cannot be null.")
    @NotEmpty(message = "The password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[_#$%.]).*$",
             message = "Password must contain at least 1 number, 1 capitalized letter and 1 special character among: _ # $ % .")
    public String password;

    @NotNull(message = "The ipAddress cannot be null.")
    @NotEmpty(message = "The ipAddress cannot be empty.")
    public String ipAddress;

    //getters
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public String getIpAddress(){
        return ipAddress;
    }


    //setters
    public void setPassword(String password){
        this.password=password;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress=ipAddress;
    }


    //constructor

    public UserRequest() {
        // Empty default constructor
    }
    public UserRequest(String ipAddress, String username, String password) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
    }

}
