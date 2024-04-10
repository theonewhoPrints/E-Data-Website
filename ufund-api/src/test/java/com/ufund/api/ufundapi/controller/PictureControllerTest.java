package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.persistence.PictureDAO;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.ufund.api.ufundapi.model.Picture;
import com.ufund.api.ufundapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class PictureControllerTest {

    private PictureController controller;
    private PictureDAO mockPictureDao;
    private UserDAO mockUserDao;

    /**
     * Sets up the environment for testing {@link PictureController}.
     * Initializes mocks for {@link PictureDAO} and {@link UserDAO}.
     *
     * @throws StreamReadException if a streaming read error occurs
     * @throws DatabindException if a data binding error occurs
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    void setupPictureController() throws StreamReadException, DatabindException, IOException {
        mockPictureDao = mock(PictureDAO.class);
        mockUserDao = mock(UserDAO.class);
        controller = new PictureController(mockPictureDao, mockUserDao);
    }

    /**
     * Tests successful change of user's picture in {@link PictureController}.
     * 
     * This method simulates a successful update of a user's picture and checks the response status and body.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void changeUserPicture_Success() throws IOException {
        // Setup
        Picture testPicture = new Picture();
        testPicture.setData(new byte[]{1, 2, 3, 4, 5});
        when(mockPictureDao.savePicture(anyString(), any())).thenReturn(testPicture);

        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4, 5});

        User user = new User(1,"testUser", "ROLE_HELPER", "", "", List.of("Bounty Hunter"));
        when(mockUserDao.findUser("testUser")).thenReturn(user);

        // Invoke
        ResponseEntity<byte[]> response = controller.changeUserPicture("testUser", "testImage", file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(testPicture.getData(), response.getBody());
    }

    /**
     * Tests changing a user's picture when the picture data is not found in {@link PictureDAO}.
     * 
     * Simulates a scenario where the picture to be saved is not found, expecting a NOT_FOUND response status.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void changeUserPicture_NotFound() throws IOException {
        // Setup
        when(mockPictureDao.savePicture(anyString(), any())).thenReturn(null);

        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4, 5});

        // Invoke
        ResponseEntity<byte[]> response = controller.changeUserPicture("testUser", "testImage", file);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests error handling in {@link PictureController} when changing a user's picture fails due to an I/O exception.
     * 
     * Verifies that an INTERNAL_SERVER_ERROR status is returned when an {@link IOException} is thrown.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void changeUserPicture_Error() throws IOException {
        // Setup
        when(mockPictureDao.savePicture(anyString(), any())).thenThrow(IOException.class);

        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4, 5});

        // Invoke
        ResponseEntity<byte[]> response = controller.changeUserPicture("testUser", "testImage", file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests retrieving a picture from {@link PictureDAO} when the picture exists.
     * 
     * Checks that the correct picture data is returned with an OK status when the picture is available.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPicture_PictureExists() throws IOException {
        // Arrange
        String imageName = "test.jpg";
        Picture picture = new Picture();
        picture.setId(imageName);
        picture.setData(new byte[]{1, 2, 3, 4, 5});
        when(mockPictureDao.getPicture(imageName)).thenReturn(picture);

        // Act
        ResponseEntity<byte[]> response = controller.getPicture(imageName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(picture.getData(), response.getBody());
    }

    /**
     * Tests the retrieval of a non-existing picture from {@link PictureDAO}.
     * 
     * Ensures that a NOT_FOUND status is returned when the requested picture does not exist.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPicture_PictureDoesNotExist() throws IOException {
        // Arrange
        String imageName = "test.jpg";
        when(mockPictureDao.getPicture(imageName)).thenReturn(null);

        // Act
        ResponseEntity<byte[]> response = controller.getPicture(imageName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests getting a picture by user name when both the user and the picture exist in {@link PictureDAO} and {@link UserDAO}.
     * 
     * Verifies that the correct picture data is returned with an OK status for a valid user name.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPictureByName_UserExists_PictureExists() throws IOException {
        // Arrange
        String name = "Evan";
        User user = new User(1, name, "ROLE_HELPER", "test.jpg", "", List.of("Bounty Hunter"));
        String imageName = "test.jpg";
        Picture picture = new Picture();
        picture.setId(imageName);
        picture.setData(new byte[]{1, 2, 3, 4, 5});
        when(mockUserDao.findUser(name)).thenReturn(user);
        when(mockPictureDao.getPicture(user.getImgUrl())).thenReturn(picture);

        // Act
        ResponseEntity<byte[]> response = controller.getPictureByName(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(picture.getData(), response.getBody());
    }

    /**
     * Tests getting a picture by user name when the user exists but the picture does not in {@link PictureDAO}.
     * 
     * Checks that a NOT_FOUND status is returned when the picture associated with the user is not found.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPictureByName_UserExists_PictureDoesNotExist() throws IOException {
        // Arrange

        String name = "testUser";
        User user = new User(1, name, "ROLE_HELPER", "test.jpg", "", List.of("Bounty Hunter"));
        when(mockUserDao.findUser(name)).thenReturn(user);
        when(mockPictureDao.getPicture(user.getImgUrl())).thenReturn(null);

        // Act
        ResponseEntity<byte[]> response = controller.getPictureByName(name);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the retrieval of a picture by user name when the user does not exist in {@link UserDAO}.
     * 
     * Asserts that a NOT_FOUND status is returned when no user is found for the given name.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPictureByName_UserDoesNotExist() throws IOException {
        // Arrange
        String name = "testUser";
        when(mockUserDao.findUser(name)).thenReturn(null);

        // Act
        ResponseEntity<byte[]> response = controller.getPictureByName(name);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests exception handling in {@code getPictureByName(String)} when an error occurs.
     * <p>
     * Verifies that an INTERNAL_SERVER_ERROR status is returned when an {@link IOException} is thrown.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void getPictureByName_HandleException() throws IOException {
        // Arrange
        String name = "testUser";
        when(mockUserDao.findUser(name)).thenThrow(IOException.class);

        // Act
        ResponseEntity<byte[]> response = controller.getPictureByName(name);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}