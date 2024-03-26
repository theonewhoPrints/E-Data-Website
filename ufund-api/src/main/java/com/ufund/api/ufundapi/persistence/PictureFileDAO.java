package com.ufund.api.ufundapi.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    @Override
    public boolean deletePicture(String id) throws IOException {
        Path filePath = Paths.get(PICTURE_DIRECTORY, id);
        if (!Files.exists(filePath)) {
            return false;
        }

        Files.delete(filePath);
        return true;
    }

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