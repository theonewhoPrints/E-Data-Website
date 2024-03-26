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
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/{id}")
    public ResponseEntity<Picture> updatePicture(@PathVariable String id, @RequestBody Picture picture) {
        try {
            Picture updatedPicture = pictureDao.updatePicture(id, picture);
            if (updatedPicture != null)
                return new ResponseEntity<>(updatedPicture, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePicture(@PathVariable String id) {
        try {
            boolean deleted = pictureDao.deletePicture(id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Picture> uploadPicture(@RequestBody Picture picture) {
        try {
            Picture uploadedPicture = pictureDao.savePicture(picture);
            if (uploadedPicture != null)
                return new ResponseEntity<>(uploadedPicture, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}