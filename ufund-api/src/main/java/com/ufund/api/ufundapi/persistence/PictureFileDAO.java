package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ufund.api.ufundapi.model.Picture;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implements the functionality for file-based persistence for Pictures
 * 
 * @author Evan Kinsey
 */
@Component
public class PictureFileDAO implements PictureDAO {
    private static String PICTURE_DIRECTORY = "data/pictures";

    public void setPictureDirectory(String pictureDirectory) {
        PICTURE_DIRECTORY = pictureDirectory;
    }

    /**
     * Retrieves a picture by its ID.
     *
     * @param id The ID of the picture to retrieve.
     * @return The Picture object if found, or null if not found.
     * @throws IOException If an I/O error occurs while reading the picture file.
     */
    @Override
    public Picture getPicture(String id) throws IOException {
        Path filePath = Paths.get(PICTURE_DIRECTORY, id);
        if (!Files.exists(filePath)) {
            return null;
        }

        byte[] data = Files.readAllBytes(filePath);

        Picture picture = new Picture();
        picture.setId(id);
        picture.setData(data);

        return picture;
    }

    /**
     * Saves a picture with the given image name and file.
     *
     * @param imageName The name of the image to save.
     * @param file      The file containing the picture data to save.
     * @return The saved Picture object.
     * @throws IOException If an I/O error occurs while saving the picture file.
     */
    @Override
    public Picture savePicture(String imageName, MultipartFile file) throws IOException {
        try {
            // Use the ID as the filename
            Path filePath = Paths.get(PICTURE_DIRECTORY, imageName);
            Files.write(filePath, file.getBytes());

            // Set the picture's ID to the generated ID
            Picture picture = new Picture();
            picture.setId(imageName);
            picture.setData(file.getBytes());
            return picture;
        } catch (IOException e) {
            throw new IOException("Failed to save picture: " + e.getMessage());
        }
    }
}