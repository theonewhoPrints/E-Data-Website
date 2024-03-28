package com.ufund.api.ufundapi.model;

public class Picture {
    private String id;
    private byte[] data;

    /**
     * Retrieves the id of the picture.
     * 
     * @return The id of the picture.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the picture.
     * 
     * @param id The id of the picture.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the data of the picture.
     * 
     * @return The data of the picture.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the data of the picture.
     * 
     * @param data The data of the picture.
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}