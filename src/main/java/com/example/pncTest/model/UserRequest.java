package com.example.pncTest.model;
import jakarta.validation.constraints.*;
public class UserRequest {
    @NotNull(message = "The username cannot be null.")
    @NotEmpty(message = "The username cannot be empty..")
    public String username;

    @NotNull(message = "The password cannot be null.")
    @NotEmpty(message = "The password cannot be empty.")
    public String password;

    @NotNull(message = "The ipAddress cannot be null.")
    @NotEmpty(message = "The ipAddress cannot be empty.")
    public String ipAddress;

    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return password;
    }
    public String getIpAddress(){
        return ipAddress;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress=ipAddress;
    }

    public UserRequest(String ipAddress, String username, String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
    }

}
