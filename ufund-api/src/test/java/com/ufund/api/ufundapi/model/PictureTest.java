package com.ufund.api.ufundapi.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Picture} class.
 * 
 * This class provides test cases for the functionality provided by the {@link Picture} class.
 * Each test method verifies a specific aspect of the {@link Picture} class's behavior.
 * 
 * @author Evan Kinsey
 */
class PictureTest {
    /**
     * Tests the ID property setting and retrieval.
     * 
     * Ensures that the ID set using {@link Picture#setId(String)} is the same as the one
     * returned by {@link Picture#getId()}.
     */
    @Test
    void testId() {
        Picture picture = new Picture();
        String id = "testId";
        picture.setId(id);
        assertEquals(id, picture.getId());
    }

    /**
     * Tests the data property setting and retrieval.
     * 
     * Verifies that the byte array set using {@link Picture#setData(byte[])} is the same as
     * the one returned by {@link Picture#getData()}. This test ensures that the data is stored
     * and retrieved correctly.
     */
    @Test
    void testData() {
        Picture picture = new Picture();
        byte[] data = {1, 2, 3, 4, 5};
        picture.setData(data);
        assertArrayEquals(data, picture.getData());
    }
}
