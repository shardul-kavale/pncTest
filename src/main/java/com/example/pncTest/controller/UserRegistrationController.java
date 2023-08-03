package com.example.pncTest.controller;

import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.ResponseData;
import com.example.pncTest.services.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class UserRegistrationController {

    private final RegistrationService registrationService;


    //Inject registration service dependency in constructor:
    public UserRegistrationController(RegistrationService registrationService){
        this.registrationService=registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest requestBody, BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            String response ="";
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                response = response + error.getDefaultMessage() + "\n";
            }
            // If validation errors exist, return the error messages
            return ResponseEntity.badRequest().body(response);
        }

      //  ResponseData response = registrationService.registerUser(requestBody);
        ResponseEntity<?> responseEntity = registrationService.registerUser(requestBody);

        //create objectMapper object for conversion of objects to json
        ObjectMapper objectMapper = new ObjectMapper();

        //set header content type for response to json:
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        if(responseEntity.getStatusCode() == HttpStatus.OK){

            ResponseData response = (ResponseData) responseEntity.getBody();

            //convert object to json
            String json = objectMapper.writeValueAsString(response);
            return ResponseEntity.ok().headers(headers).body(json);
        }
        else if((responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST)){

            Object jsonObject = objectMapper.writeValueAsString(new ResponseData("Bad Request","Only users in Canada are eligible", null));//return error here
            return ResponseEntity.badRequest().body(jsonObject);
        }
        else{
            return ResponseEntity.internalServerError().body("Sorry we have issues on our end. Try again later");
        }

        /*
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response);
        // Continue if there are no validation errors
        return ResponseEntity.ok(json);*/
       // return ResponseEntity.ok("User Registration Successful!\n\n" + "Welcome, " + response.username + "!\nUser ID: " + response.uuid +"\nLocation: " + response.city);
    }

}
