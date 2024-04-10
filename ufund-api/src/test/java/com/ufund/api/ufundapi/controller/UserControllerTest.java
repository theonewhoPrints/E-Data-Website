package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;


/**
 * Test the User Controller class
 * 
 * /
 * @author Evan Kinsey
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    /**
     * Tests retrieving all users successfully from {@link UserController}.
     * 
     * Verifies that the correct users are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during user retrieval
     */
    @Test
    public void testGetUsers() throws IOException { // getUsers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User(1,"Spike Spiegel", "ROLE_HELPER", "", "", List.of("Bounty Hunter"));
        users[1] = new User(2,"Vicious", "ROLE_VILLAIN", "", "", List.of("Bounty Hunter"));
        // When getUsers is called return the users created above
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    /**
     * Tests the exception handling in {@link UserController#getUsers()} method.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during user retrieval
     */
    @Test
    public void testGetUsersHandleException() throws IOException { // getUsers may throw IOException
        // Setup
        // When getUsers is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Tests finding a specific user by name in {@link UserController}.
     * 
     * Verifies that the correct user is returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during user search
     */
    @Test
    public void testFindUser() throws IOException {  // findUser may throw IOException
        // Setup
        // when findUser is called, it will return the name created below
        User[] users = {
            new User(1, "Spike Spiegel", "ROLE_HELPER", "", "", List.of("Bounty Hunter")),
            new User(2, "Faye Valentine", "ROLE_HELPER", "", "", List.of("Bounty Hunter")),
            new User(3, "Jet Black", "ROLE_HELPER", "", "", List.of("Bounty Hunter"))
        };
    
        // Mock behavior of findUser method
        when(mockUserDAO.findUser("Spike Spiegel")).thenReturn(users[0]);

        // Invoke the method under test
        ResponseEntity<User> response = userController.findUser("Spike Spiegel");

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users[0], response.getBody());
    }

    /**
     * Tests the exception handling in {@link UserController#findUser(String)} method.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws Exception if an error occurs during user search
     */
    @Test
    public void testFindUserHandleException() throws Exception { // findUser may throw IOException
        // Setup
        // When findUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).findUser("Ed");

        // Invoke
        ResponseEntity<User> response = userController.findUser("Ed");

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Tests updating a user successfully in {@link UserController}.
     * 
     * Verifies that the user is updated correctly and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during user update
     */
    @Test
    void updateUser_Success() throws IOException {
        User testUser = new User(0, null, null, null, null, null);
        when(mockUserDAO.updateUser(testUser)).thenReturn(testUser);

        UserController controller = new UserController(mockUserDAO);

        // Act
        ResponseEntity<User> response = controller.updateUser(testUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    /**
     * Tests updating a user in {@link UserController} when the user is not found.
     * 
     * Verifies that the HTTP status is NOT_FOUND when the user does not exist.
     * 
     * @throws IOException if an I/O error occurs during user update
     */
    @Test
    void updateUser_NotFound() throws IOException {
        User testUser = new User(0, null, null, null, null, null);
        when(mockUserDAO.updateUser(testUser)).thenReturn(null);

        UserController controller = new UserController(mockUserDAO);

        // Act
        ResponseEntity<User> response = controller.updateUser(testUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the exception handling in {@link UserController#updateUser(User)} method.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during user update
     */
    @Test
    void updateUser_Error() throws IOException {
        User testUser = new User(0, null, null, null, null, null);
        when(mockUserDAO.updateUser(testUser)).thenThrow(IOException.class);

        UserController controller = new UserController(mockUserDAO);

        // Act
        ResponseEntity<User> response = controller.updateUser(testUser);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
