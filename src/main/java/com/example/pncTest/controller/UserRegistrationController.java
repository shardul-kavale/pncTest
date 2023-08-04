package com.example.pncTest.controller;

import com.example.pncTest.model.Error;
import com.example.pncTest.model.enums.ErrorCode;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.enums.ErrorType;
import com.example.pncTest.model.enums.Status;
import com.example.pncTest.services.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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

        //set header content type for response to json:
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //create objectMapper object for conversion of objects to json
        ObjectMapper objectMapper = new ObjectMapper();

        //Handle request body validation
        if (bindingResult.hasErrors()) {

            //arraylist to store validation errors
            ArrayList<String> errorList = new ArrayList<>();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                errorList.add(error.getDefaultMessage());
            }

            //create custom error object and add all validation errors
            Error e = new Error(Status.FAILED, ErrorCode.VALIDATION_ERROR.getCode(), ErrorType.VALIDATION_ERROR, errorList);

            //convert object to json and return
            String jsonError = objectMapper.writeValueAsString(e);
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(jsonError);
        }

        //If no validation errors->
        try{
            //try registering the user, convert ResponseData to json string, return string through ResponseEntity
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(objectMapper.writeValueAsString(registrationService.registerUser(requestBody)));

        }catch(IOException e){

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body(objectMapper.writeValueAsString(
                            new Error(Status.FAILED,ErrorCode.SERVER_ERROR.getCode(),ErrorType.SERVER_ERROR, "Something's wrong on our end. Please try again later")
                         )
                    );
        }catch(IllegalArgumentException e){

            if(e.getMessage().equals("INVALID_IP")){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .headers(headers)
                        .body(objectMapper.writeValueAsString(
                                        new Error(Status.FAILED,ErrorCode.VALIDATION_ERROR.getCode(),ErrorType.VALIDATION_ERROR, "Please enter a valid IP Address")
                                )
                        );
            }else {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .headers(headers)
                        .body(objectMapper.writeValueAsString(
                                        new Error(Status.FAILED,ErrorCode.INELIGIBLE_ERROR.getCode(),ErrorType.INELIGIBLE_ERROR, "Sorry, only users in Canada are eligible to register!")
                                )
                        );
            }

        }

    }

}
