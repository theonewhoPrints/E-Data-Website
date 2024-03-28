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
     * Retrieves a picture by the user ID associated with it.
     *
     * @param userId The ID of the user associated with the picture.
     * @return The Picture object representing the retrieved picture.
     * @throws IOException If an I/O error occurs while retrieving the picture.
     */
    Picture getPictureById(String userId) throws IOException;

    /**
     * Saves a picture with the given image name and content.
     *
     * @param imageName The name of the image.
     * @param image The MultipartFile object representing the image content.
     * @return The Picture object representing the saved picture.
     * @throws IOException If an I/O error occurs while saving the picture.
     */
    Picture savePicture(String imageName, MultipartFile image) throws IOException;

    /**
     * Deletes a picture by its ID.
     *
     * @param id The ID of the picture to delete.
     * @return true if the picture was successfully deleted, false otherwise.
     * @throws IOException If an I/O error occurs while deleting the picture.
     */
    boolean deletePicture(String id) throws IOException;

    /**
     * Updates a picture with the given image name and content.
     *
     * @param imageName The name of the image.
     * @param image The MultipartFile object representing the updated image content.
     * @return The Picture object representing the updated picture.
     * @throws IOException If an I/O error occurs while updating the picture.
     */
    Picture updatePicture(String imageName, MultipartFile image) throws IOException;
}