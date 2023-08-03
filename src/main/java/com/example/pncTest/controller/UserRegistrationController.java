package com.example.pncTest.controller;

import com.example.pncTest.model.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;


@RestController
public class UserRegistrationController {

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest requestBody, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            String response ="";
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                response = response + error.getDefaultMessage() + "\n";
            }
            // If validation errors exist, return the error messages
            return ResponseEntity.badRequest().body(response);
        }

        // Continue if there are no validation errors
        return ResponseEntity.ok("User Registration Successful!");
    }

}
