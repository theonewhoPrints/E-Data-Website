package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Picture;

public interface PictureDAO {
    Picture getPicture(String id) throws IOException;
    Picture getPictureById(String userId) throws IOException;
    Picture savePicture(Picture picture) throws IOException;
    boolean deletePicture(String id) throws IOException;
    Picture updatePicture(String id, Picture picture) throws IOException;
}