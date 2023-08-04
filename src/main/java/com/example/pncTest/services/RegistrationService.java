package com.example.pncTest.services;

import com.example.pncTest.model.IPLookupPojo;
import com.example.pncTest.model.User;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.ResponseData;
import com.example.pncTest.model.enums.Status;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        validateIpAddress(request.getIpAddress());

        IPLookupPojo ip = lookupIpAddress(request.getIpAddress());
        validateCountry(ip.getCountry());

        String uuid = UUID.randomUUID().toString();
        User newUser = createUser(uuid, request.getUsername(), ip.getCity());

        //pass the created user along with messages and status to the controller
        return new ResponseData(Status.SUCCESS, "User has been registered", newUser);
    }

    //check if IP valid
    private void validateIpAddress(String ipAddress) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        if (!validator.isValid(ipAddress)) {
            throw new IllegalArgumentException("INVALID_IP");
        }
    }

    //call ipTracker service which deals with external API
    private IPLookupPojo lookupIpAddress(String ipAddress) throws IOException {
        try {
            return ipTrackerService.lookupIP(ipAddress);
        } catch (IOException e) {
            throw e;
        }
    }

    //check if user is from Canada
    private void validateCountry(String country) {
        if (!"Canada".equals(country)) {
            throw new SecurityException("INELIGIBLE");
        }
    }

    //simulate user registration/creation:
    private User createUser(String uuid, String username, String city) {
        return new User(uuid, username, city);
    }
}
