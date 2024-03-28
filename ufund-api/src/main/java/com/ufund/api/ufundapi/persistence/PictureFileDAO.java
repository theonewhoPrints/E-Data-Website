package com.ufund.api.ufundapi.persistence;

import java.io.FileNotFoundException;
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
    private static final String PICTURE_DIRECTORY = "data/pictures";
    private static final String PICTURE_JSON_DIRECTORY = "data/pictures.json";

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
     * Retrieves a picture by the user ID.
     *
     * @param userId The ID of the user associated with the picture.
     * @return The Picture object if found, or null if not found.
     * @throws IOException If an I/O error occurs while reading the picture file.
     */
    @Override
    public Picture getPictureById(String userId) throws IOException {
        Path filePath = Paths.get(PICTURE_JSON_DIRECTORY, userId);
        if (!Files.exists(filePath)) {
            return null;
        }

        byte[] data = Files.readAllBytes(filePath);

        Picture picture = new Picture();
        picture.setId(userId);
        picture.setData(data);

        return picture;
    }

    /**
     * Deletes a picture by its ID.
     *
     * @param id The ID of the picture to delete.
     * @return true if the picture is successfully deleted, false if the picture does not exist.
     * @throws IOException If an I/O error occurs while deleting the picture file.
     */
    @Override
    public boolean deletePicture(String id) throws IOException {
        Path filePath = Paths.get(PICTURE_DIRECTORY, id);
        if (!Files.exists(filePath)) {
            return false;
        }

        Files.delete(filePath);
        return true;
    }

    /**
     * Updates a picture with the given image name and file.
     *
     * @param imageName The name of the image to update.
     * @param file      The new file containing the updated picture data.
     * @return The updated Picture object.
     * @throws IOException            If an I/O error occurs while updating the picture file.
     * @throws FileNotFoundException If the picture file with the given image name does not exist.
     */
    @Override
    public Picture updatePicture(String imageName, MultipartFile file) throws IOException {
        Path filePath = Paths.get(PICTURE_DIRECTORY, imageName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("No picture found with the given id: " + imageName);
        }
        Files.write(filePath, file.getBytes());

        Picture picture = new Picture();
        picture.setId(imageName);
        picture.setData(file.getBytes()); // Assuming Picture has a setData method
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

        // Use the ID as the filename
        Path filePath = Paths.get(PICTURE_DIRECTORY, imageName);
        Files.write(filePath, file.getBytes());

        // Set the picture's ID to the generated ID
        Picture picture = new Picture();
        picture.setId(imageName);
        picture.setData(file.getBytes());
        return picture;
    }
}