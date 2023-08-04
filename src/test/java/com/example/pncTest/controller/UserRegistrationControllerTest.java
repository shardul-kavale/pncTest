package com.example.pncTest.controller;

import com.example.pncTest.model.Error;
import com.example.pncTest.model.ResponseData;
import com.example.pncTest.model.User;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.enums.ErrorCode;
import com.example.pncTest.model.enums.ErrorType;
import com.example.pncTest.model.enums.Status;
import com.example.pncTest.services.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegisterUser_ValidRequest_Success() throws Exception {
        UserRequest userRequest = new UserRequest("100.42.23.255", "JohnDoe", "She#22wadr");
        User user = new User("UUID123", "JohnDoe", "Ottawa");
        ResponseData responseData = new ResponseData(Status.SUCCESS, "User has been registered", user);
        when(registrationService.registerUser(any(UserRequest.class))).thenReturn(responseData);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(Status.SUCCESS.toString()))
                .andExpect(jsonPath("$.message").value("User has been registered"))
                .andExpect(jsonPath("$.user.uuid").value("UUID123"))
                .andExpect(jsonPath("$.user.username").value("JohnDoe"))
                .andExpect(jsonPath("$.user.city").value("Ottawa"));
    }

    @Test
    public void testRegisterUser_InvalidIpAddress() throws Exception {
        UserRequest userRequest = new UserRequest("invalid_ip", "JohnDoe", "She#22wadr");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(Status.FAILED.toString()))
                .andExpect(jsonPath("$.description").value("Please enter a valid IP Address"));
    }

    @Test
    public void testRegisterUser_IneligibleCountry() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest("100.45.23.255", "JohnDoe", "She#22wadr");

        // Mock the behavior of registrationService to throw a SecurityException for an ineligible user
        when(registrationService.registerUser(any(UserRequest.class)))
                .thenThrow(new SecurityException("INELIGIBLE"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(Status.FAILED.toString()))
                .andExpect(jsonPath("$.description").value("Sorry, only users in Canada are eligible to register!"));
    }


}