package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ufund.api.ufundapi.model.Picture;

/**
 * The PictureDAO interface provides methods to interact with picture data in the persistence layer.
 */
public interface PictureDAO {
    /**
     * Retrieves a picture by its ID.
     *
     * @param id The ID of the picture to retrieve.
     * @return The Picture object representing the retrieved picture.
     * @throws IOException If an I/O error occurs while retrieving the picture.
     */
    Picture getPicture(String id) throws IOException;

    /**
     * Saves a picture with the given image name and content.
     *
     * @param imageName The name of the image.
     * @param image The MultipartFile object representing the image content.
     * @return The Picture object representing the saved picture.
     * @throws IOException If an I/O error occurs while saving the picture.
     */
    Picture savePicture(String imageName, MultipartFile image) throws IOException;
}