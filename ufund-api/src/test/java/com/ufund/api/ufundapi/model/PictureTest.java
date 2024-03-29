package com.ufund.api.ufundapi.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PictureTest {

    @Test
    void testId() {
        Picture picture = new Picture();
        String id = "testId";
        picture.setId(id);
        assertEquals(id, picture.getId());
    }

    @Test
    void testData() {
        Picture picture = new Picture();
        byte[] data = {1, 2, 3, 4, 5};
        picture.setData(data);
        assertArrayEquals(data, picture.getData());
    }
}
