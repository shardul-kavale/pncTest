package com.example.pncTest.services;

import com.example.pncTest.model.IPLookupPojo;
import com.example.pncTest.model.ResponseData;
import com.example.pncTest.model.UserRequest;
import com.example.pncTest.model.enums.Status;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    //mock external dependency:
    @Mock
    private IpTrackerService ipTrackerService;

    //inject that dependency into this class that we are testing:
    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testRegisterUser_ValidRequest_Success() throws IOException {
        // Arrange
        UserRequest userRequest = new UserRequest("100.42.23.255", "JohnDoe", "She#22wadr");
        when(ipTrackerService.lookupIP("100.42.23.255"))
                .thenReturn(new IPLookupPojo("success", "Canada", "CA", "ON", "Ontario", "Ottawa", "B3H2T1", "40.7128", "-74.0060", "Canada/Toronto", "ISP", "Organization", "AS123", "100.42.23.255"));

        // Act
        ResponseData responseData = registrationService.registerUser(userRequest);

        // Assert
        assertNotNull(responseData);
        assertEquals(Status.SUCCESS, responseData.getStatus());
        assertEquals("User has been registered", responseData.getMessage());
        assertNotNull(responseData.getUser());
        assertEquals("JohnDoe", responseData.getUser().getUsername());
        assertEquals("Ottawa", responseData.getUser().getCity());
    }

    @Test
    public void testRegisterUser_IneligibleCountry() throws IOException {
        // Arrange
        UserRequest userRequest = new UserRequest("100.45.23.255", "JohnDoe", "She#22wadr");
        when(ipTrackerService.lookupIP("100.45.23.255"))
                .thenReturn((new IPLookupPojo("success", "US", "USA", "GA", "Georgia", "Atlanta", "10001", "40.7128", "-74.0060", "America/New_York", "ISP", "Organization", "AS123", "100.45.23.255")));
        // Act and Assert
        assertThrows(SecurityException.class, () -> registrationService.registerUser(userRequest));
    }

    @Test
    public void testRegisterUser_InvalidIpAddress() {
        // Arrange
        UserRequest userRequest = new UserRequest("xxxzzzzz", "JohnDoe", "She#22wadr");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> registrationService.registerUser(userRequest));
    }

    @Test
    public void testRegisterUser_IPLookupFailure() throws IOException {
        // Arrange
        UserRequest userRequest = new UserRequest("100.42.23.255", "JohnDoe", "She#22wadr");
        when(ipTrackerService.lookupIP("100.42.23.255"))
                .thenThrow(new IOException("Internal Server Error"));

        // Act and Assert
        assertThrows(IOException.class, () -> registrationService.registerUser(userRequest));
    }


}