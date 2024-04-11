package com.ufund.api.ufundapi.persistence;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link UserFileDAO}.
 * Tests cover various scenarios for users.
 * 
 * @author Evan Kinsey
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {
    private UserFileDAO userFileDAO;
    private ObjectMapper mockObjectMapper;
    private User[] testUsers;

    /**
     * Sets up the testing environment before each test.
     * This includes preparing a mock {@link ObjectMapper} and initializing test user data.
     *
     * @throws IOException if there is an issue setting up the mock data
     */
    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[]{
            new User(1, "John Doe", "johndoe@example.com", "", "", List.of("")),
            new User(2, "Jane Doe", "janedoe@example.com", "", "", List.of(""))
        };

        when(mockObjectMapper.readValue(new File("users.json"), User[].class))
            .thenReturn(testUsers);

        userFileDAO = new UserFileDAO("users.json", mockObjectMapper);
    }

    /**
     * Tests retrieval of all users from storage.
     * 
     * Verifies that the retrieved users match the expected test users array.
     */
    @Test
    public void testGetUsers() {
        User[] users = userFileDAO.getUsers();
        assertArrayEquals(testUsers, users, "The returned users should match the test users.");
    }

    /**
     * Tests finding an existing user by name.
     * 
     * Verifies that the correct user is returned when searching for an existing user name.
     */
    @Test
    public void testFindUser_Existing() {
        String name = "John Doe";
        User user = userFileDAO.findUser(name);
        assertNotNull(user, "A user should be found with the name " + name);
        assertEquals(name, user.getName(), "The found user should have the name " + name);
    }

    /**
     * Tests the behavior when searching for a non-existing user.
     * 
     * Verifies that {@code null} is returned when searching for a user name that does not exist.
     */
    @Test
    public void testFindUser_NonExisting() {
        String name = "Non Existing User";
        User user = userFileDAO.findUser(name);
        assertNull(user, "No user should be found with the name " + name);
    }

    /**
     * Tests the behavior when an IOException occurs during user data loading.
     * 
     * Verifies that an {@link IOException} is thrown when there is an issue reading the user file.
     */
    @Test
    public void testLoad_IOException() throws IOException {
        doThrow(new IOException("Failed to read file"))
            .when(mockObjectMapper)
            .readValue(new File("users.json"), User[].class);

        assertThrows(IOException.class, () -> new UserFileDAO("users.json", mockObjectMapper),
                     "Loading users should throw IOException on file read failure");
    }

    /**
     * Tests updating a user that exists in the storage.
     * 
     * Verifies that the user's data is updated correctly and the new data is reflected in the returned user object.
     */
    @Test
    void updateUser_UserExists() throws IOException {
        // Arrange
        User user = new User(1, "test", "test", "test", "test", List.of("test"));
        user.setImgUrl("hello");

        // Act
        User updatedUser = userFileDAO.updateUser(user);

        // Assert
        assertEquals("hello", updatedUser.getImgUrl());
    }

    /**
     * Tests updating a user that does not exist in the storage.
     * 
     * Verifies that {@code null} is returned when attempting to update a user that does not exist.
     */
    @Test
    void updateUser_UserDoesNotExist() throws IOException {
        // Arrange
        User user = new User(99, "idNotInMock", "test", "test", "test", List.of("test"));

        // Act
        User updatedUser = userFileDAO.updateUser(user);

        // Assert
        assertNull(updatedUser);
    }

}
