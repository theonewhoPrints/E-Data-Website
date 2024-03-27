package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Picture;
import com.ufund.api.ufundapi.persistence.PictureDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.ufund.api.ufundapi.persistence.UserDAO;
import com.ufund.api.ufundapi.model.User;

@RestController
@RequestMapping("picture")
public class PictureController {
    private static final Logger LOG = Logger.getLogger(PictureController.class.getName());
    
    private PictureDAO pictureDao;
    private UserDAO userDao;

    public PictureController(PictureDAO pictureDAO, UserDAO userDao) {
        this.pictureDao = pictureDAO;
        this.userDao = userDao;
    }

    /* 
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

    /* 
     * 
     * example: http://localhost:8080/picture/Evan
    */
    @GetMapping("/{name}")
    public ResponseEntity<byte[]> getPictureByName(@PathVariable String name) throws IOException {
        LOG.info("GET /picture/" + name);

        try {
            User user = userDao.findUser(name);
            String img = user.getImgUrl();

            if (img == null){
                img = "default.jpg";
            }
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

    @PutMapping("/{imageName}")
    public ResponseEntity<byte[]> updatePicture(@PathVariable String imageName, @RequestPart("image") MultipartFile image) {
        LOG.info("PUT /picture/" + imageName);
        try {
            Picture updatedPicture = pictureDao.updatePicture(imageName, image);
            byte[] data;
            if (updatedPicture != null) {
                data = updatedPicture.getData();
                // Return the image
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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