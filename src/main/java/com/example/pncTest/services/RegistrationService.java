package com.example.pncTest.services;

import com.example.pncTest.model.IPLookupPojo;
import com.example.pncTest.model.User;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.ResponseData;
import com.example.pncTest.model.enums.Status;
import org.apache.commons.validator.routines.InetAddressValidator;
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
    public ResponseData registerUser(UserRequest request) throws IOException {

        //IP validation:
        InetAddressValidator validator = InetAddressValidator.getInstance();

        if(!validator.isValid(request.getIpAddress())){
            throw new IllegalArgumentException("INVALID_IP");
        }

        //find city and country by calling the service
        String city;
        String country;
        try {
            IPLookupPojo ip = ipTrackerService.lookupIP((request.getIpAddress()));
            country=ip.getCountry();
            city= ip.getCity();

        } catch (IOException e) {
              throw e; //forward the exception to the controller where we handle it
        }

        if (!country.equals("Canada")) {
            throw new IllegalArgumentException("INELIGIBLE");
        }

        String uuid = UUID.randomUUID().toString();

        //simulate user registration/creation:
        User u1 = new User(uuid, request.username, city);

        //pass the created user along with messages and status to the controller
        return new ResponseData(Status.SUCCESS, "User has been registered", u1);
    }
}
