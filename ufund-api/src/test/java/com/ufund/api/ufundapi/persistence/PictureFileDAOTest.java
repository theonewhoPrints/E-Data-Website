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

class PictureFileDAOTest {

    private PictureFileDAO pictureFileDAO;
    private static final String PICTURE_DIRECTORY = "src/test/resources/pictures";

    @BeforeEach
    void setupPictureFileDAO() {
        pictureFileDAO = new PictureFileDAO();
        pictureFileDAO.setPictureDirectory(PICTURE_DIRECTORY);
    }

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

    @Test
    void getPicture_PictureDoesNotExist() throws IOException{
        // Arrange
        String id = "nonexistent.jpg";

        // Act
        Picture picture = pictureFileDAO.getPicture(id);

        // Assert
        assertNull(picture);
    }

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