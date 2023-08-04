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

@RestController
public class UserRegistrationController {

    private final RegistrationService registrationService;

    //Inject registration service and objectMapper dependency in constructor:
    public UserRegistrationController(RegistrationService registrationService){
        this.registrationService=registrationService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest requestBody, BindingResult bindingResult) throws JsonProcessingException {

        //set header content type for response to json:
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Handle request body validation
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult, headers);
        }

        //If no validation errors->
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(registrationService.registerUser(requestBody));
        } catch (IOException e) {
            return handleServerError("Something's wrong on our end. Please try again later",headers);
        } catch (IllegalArgumentException e) {
            return handleValidationError("Please enter a valid IP Address", headers);
        } catch (SecurityException e) {
            return handleIneligibleError("Sorry, only users in Canada are eligible to register!", headers);
        }
    }

    private ResponseEntity<Error> handleValidationErrors(BindingResult bindingResult, HttpHeaders headers) throws JsonProcessingException {

        //arraylist to store validation errors
        List<String> errorList = new ArrayList<>();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorList.add(error.getDefaultMessage());
        }

        //create custom error object
        Error error = new Error(Status.FAILED, ErrorCode.VALIDATION_ERROR.getCode(), ErrorType.VALIDATION_ERROR, errorList);
        return ResponseEntity.badRequest().headers(headers).body(error);
    }

    private ResponseEntity<Error> handleServerError(String message,HttpHeaders headers) throws JsonProcessingException {
        Error error = new Error(Status.FAILED, ErrorCode.SERVER_ERROR.getCode(), ErrorType.SERVER_ERROR, message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(error);
    }

    private ResponseEntity<Error> handleValidationError(String message, HttpHeaders headers) throws JsonProcessingException {
        Error error = new Error(Status.FAILED, ErrorCode.VALIDATION_ERROR.getCode(), ErrorType.VALIDATION_ERROR, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(error);
    }

    private ResponseEntity<Error> handleIneligibleError(String message, HttpHeaders headers) throws JsonProcessingException {
        Error error = new Error(Status.FAILED, ErrorCode.INELIGIBLE_ERROR.getCode(), ErrorType.INELIGIBLE_ERROR, message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body(error);
    }

}
