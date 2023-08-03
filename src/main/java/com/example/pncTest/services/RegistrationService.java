package com.example.pncTest.services;

import com.example.pncTest.model.IPLookupPojo;
import com.example.pncTest.model.User;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {


    //inject ipTrackerService:
    private final IpTrackerService ipTrackerService;

    public RegistrationService(IpTrackerService ipTrackerService){
        this.ipTrackerService=ipTrackerService;
    }
    public ResponseEntity<?> registerUser(UserRequest request)  {

        String uuid = UUID.randomUUID().toString();

        //find city and country by calling the service
        String city;
        String country;
        try {
            IPLookupPojo ip = ipTrackerService.lookupIP((request.getIpAddress()));
            country=ip.getCountry();
            city= ip.getCity();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        try{
            if (!country.equals("Canada")) {
                throw new IllegalArgumentException("User is not eligible to register.");
            }
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }




        //simulate user registration/creation:
        User u1 = new User(uuid, request.username, city);

        //pass the created user along with messages and status to the controller
        return ResponseEntity.ok().body(new ResponseData("Success", "User has been registered", u1));
       // return new ResponseData("Success", "User has been registered", u1);
    }
}
