package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ufund.api.ufundapi.model.Picture;

public interface PictureDAO {
    Picture getPicture(String id) throws IOException;
    Picture getPictureById(String userId) throws IOException;
    Picture savePicture(String imageName, MultipartFile image) throws IOException;
    boolean deletePicture(String id) throws IOException;
    Picture updatePicture(String imageName, MultipartFile image) throws IOException;
}