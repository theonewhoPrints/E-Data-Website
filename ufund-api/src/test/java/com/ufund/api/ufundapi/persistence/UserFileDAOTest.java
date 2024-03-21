package com.ufund.api.ufundapi.persistence;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-tier")
public class UserFileDAOTest {
    private UserFileDAO userFileDAO;
    private ObjectMapper mockObjectMapper;
    private User[] testUsers;

    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[]{
            new User(1, "John Doe", "johndoe@example.com"),
            new User(2, "Jane Doe", "janedoe@example.com")
        };

        when(mockObjectMapper.readValue(new File("users.json"), User[].class))
            .thenReturn(testUsers);

        userFileDAO = new UserFileDAO("users.json", mockObjectMapper);
    }

    @Test
    public void testGetUsers() {
        User[] users = userFileDAO.getUsers();
        assertArrayEquals(testUsers, users, "The returned users should match the test users.");
    }

    @Test
    public void testFindUser_Existing() {
        String name = "John Doe";
        User user = userFileDAO.findUser(name);
        assertNotNull(user, "A user should be found with the name " + name);
        assertEquals(name, user.getName(), "The found user should have the name " + name);
    }

    @Test
    public void testFindUser_NonExisting() {
        String name = "Non Existing User";
        User user = userFileDAO.findUser(name);
        assertNull(user, "No user should be found with the name " + name);
    }

    @Test
    public void testLoad_IOException() throws IOException {
        doThrow(new IOException("Failed to read file"))
            .when(mockObjectMapper)
            .readValue(new File("users.json"), User[].class);

        assertThrows(IOException.class, () -> new UserFileDAO("users.json", mockObjectMapper),
                     "Loading users should throw IOException on file read failure");
    }

}
