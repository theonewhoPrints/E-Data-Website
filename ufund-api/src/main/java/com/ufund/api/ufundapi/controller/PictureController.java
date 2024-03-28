package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Picture;
import com.ufund.api.ufundapi.persistence.PictureDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.User;

/**
 * Handles the REST API requests for the Picture resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Evan Kinsey 
 */
@RestController
@RequestMapping("picture")
public class PictureController {
    private static final Logger LOG = Logger.getLogger(PictureController.class.getName());

    private PictureDAO pictureDao;
    private UserDAO userDao;

    /**
     * Constructs a new PictureController with the specified PictureDAO and UserDAO.
     * 
     * @param pictureDAO the PictureDAO object to be used
     * @param userDao the UserDAO object to be used
     */
    public PictureController(PictureDAO pictureDAO, UserDAO userDao) {
        this.pictureDao = pictureDAO;
        this.userDao = userDao;
    }


    /**
     * Retrieves the picture data for the specified image name.
     *
     * @param imageName The name of the image to retrieve.
     * @return A ResponseEntity containing the picture data as a byte array.
     * @throws IOException If an I/O error occurs while retrieving the picture data.
     * 
     * example: http://localhost:8080/picture/image/default.jpg
     */
    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getPicture(@PathVariable String imageName) throws IOException {
        // Use the PictureFileDAO to get the Picture object
        Picture picture = pictureDao.getPicture(imageName);
    
        if (picture == null) {
            // Return 404 Not Found if the picture does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        byte[] data = picture.getData();
        // Return the image
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
    }

    /**
     * Retrieves a picture by its name.
     *
     * @param name the name of the picture
     * @return a ResponseEntity containing the picture data if found, or a 404 Not Found status if the picture does not exist
     * @throws IOException if an I/O error occurs while retrieving the picture
     * 
     * example: http://localhost:8080/picture/Evan
     */
    @GetMapping("/{name}")
    public ResponseEntity<byte[]> getPictureByName(@PathVariable String name) throws IOException {
        LOG.info("GET /picture/" + name);

        try {
            User user = userDao.findUser(name);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            String img = user.getImgUrl();
            
            // Use the PictureFileDAO to get the Picture object
            Picture picture = pictureDao.getPicture(img);

            if (picture == null) {
                // Return 404 Not Found if the picture does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        
            byte[] data = picture.getData();
            // Return the image
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);

        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Changes the user's picture with the specified image.
     *
     * @param username The username of the user.
     * @param imageName The name of the image.
     * @param image The image file to be uploaded.
     * @return A ResponseEntity containing the byte array of the uploaded image if successful, or a NOT_FOUND status if the picture was not uploaded.
     */
    @PostMapping("/{username}/{imageName}")
    public ResponseEntity<byte[]> changeUserPicture(@PathVariable String username, @PathVariable String imageName, @RequestPart("image") MultipartFile image) {
        LOG.info("POST /picture/" + imageName);
        try {
            Picture uploadedPicture = pictureDao.savePicture(imageName, image);
            byte[] data;
            if (uploadedPicture != null) {
                data = uploadedPicture.getData();

                User user = userDao.findUser(username);
                user.setImgUrl(imageName);
                userDao.updateUser(user);
                // Return the image
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}