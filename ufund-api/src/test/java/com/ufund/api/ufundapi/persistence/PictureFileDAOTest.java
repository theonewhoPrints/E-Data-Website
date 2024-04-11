package com.ufund.api.ufundapi.persistence;

import com.ufund.api.ufundapi.model.Picture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PictureFileDAO}.
 * Tests cover various scenarios for saving and retrieving pictures.
 * 
 * @author Evan Kinsey
 */
class PictureFileDAOTest {

    private PictureFileDAO pictureFileDAO;
    private static final String PICTURE_DIRECTORY = "src/test/resources/pictures";

    /**
     * Sets up {@link PictureFileDAO} with a specified picture directory before each test.
     */
    @BeforeEach
    void setupPictureFileDAO() {
        pictureFileDAO = new PictureFileDAO();
        pictureFileDAO.setPictureDirectory(PICTURE_DIRECTORY);
    }

    /**
     * Tests retrieving a picture that exists.
     * 
     * Verifies that the correct picture data and id are returned when the picture exists in the directory.
     *
     * @throws IOException if an I/O error occurs during picture retrieval
     */
    @Test
    void getPicture_PictureExists() throws IOException {
        // Arrange
        String id = "test.jpg";
        Path filePath = Paths.get(PICTURE_DIRECTORY, id);
        byte[] data = Files.readAllBytes(filePath);

        // Act
        Picture picture = pictureFileDAO.getPicture(id);

        // Assert
        assertEquals(id, picture.getId());
        assertArrayEquals(data, picture.getData());
    }

    /**
     * Tests retrieving a picture that does not exist.
     * 
     * Verifies that {@code null} is returned when the picture does not exist in the directory.
     *
     * @throws IOException if an I/O error occurs during picture retrieval
     */
    @Test
    void getPicture_PictureDoesNotExist() throws IOException{
        // Arrange
        String id = "nonexistent.jpg";

        // Act
        Picture picture = pictureFileDAO.getPicture(id);

        // Assert
        assertNull(picture);
    }

    /**
     * Tests saving a picture successfully.
     * 
     * Verifies that the picture is saved correctly and the same picture data is returned.
     *
     * @throws IOException if an I/O error occurs during picture saving
     */
    @Test
    void savePicture_Success() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4, 5});
        Path filePath = Paths.get(PICTURE_DIRECTORY, "test.jpg");
        Files.createDirectories(filePath.getParent());

        // Act
        Picture picture = pictureFileDAO.savePicture("test.jpg", file);

        // Assert
        assertEquals("test.jpg", picture.getId());
        assertArrayEquals(file.getBytes(), picture.getData());
    }

    /**
     * Tests error handling in saving a picture when an error occurs.
     * 
     * Verifies that an {@link IOException} is thrown when trying to save a picture to an invalid directory.
     */
    @Test
    void savePicture_Error() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{1, 2, 3, 4, 5});

        // Act & Assert
        assertThrows(IOException.class, () -> {
            // Simulate an error by passing an invalid picture directory
            pictureFileDAO.setPictureDirectory("invalid/directory");
            pictureFileDAO.savePicture("test.jpg", file);
        });
    }
}