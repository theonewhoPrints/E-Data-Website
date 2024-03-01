package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Scheme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Hero File DAO class
 * 
 * @author Isaac Soares & Nadeem Mustafa 
 */


@Tag("Persistence-tier")
public class VillainFileDAOTest {
    VillainFileDAO VillainFileDAO;
    Scheme[] testSchemes;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testSchemes = new Scheme[3];
        testSchemes[0] = new Scheme(99,"Dr. Freeze", "Freeze America");
        testSchemes[1] = new Scheme(100,"Agental00", "Freeze 2K20");
        testSchemes[2] = new Scheme(101,"TSiftal", "waste all carbon emissions");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Scheme[].class))
                .thenReturn(testSchemes);
        VillainFileDAO = new VillainFileDAO("doesnt_matter.txt",mockObjectMapper);

    }

    /**
    * Test method to verify the behavior of {@code getSchemes()} method in {@code VillainFileDAO}.
    * It invokes the method and compares the returned schemes with the test schemes.
    */
    @Test
    public void testGetSchemes() {
        // Invoke
        Scheme[] schemes = VillainFileDAO.getSchemes();

        // Analyze
        assertEquals(schemes.length,testSchemes.length);
        for (int i = 0; i < testSchemes.length;++i)
            assertEquals(schemes[i],testSchemes[i]);
    }

    /**
    * Test method to verify the behavior of {@code findSchemes(String)} method in {@code VillainFileDAO}.
    * It invokes the method with a search term and verifies the returned schemes.
    */
    @Test
    public void testFindSchemes() {   //may have to make more for different finds in each text.
        // Invoke
        Scheme[] schemes = VillainFileDAO.findSchemes("al");

        // Analyze
        assertEquals(schemes.length,2);
        assertEquals(schemes[0],testSchemes[1]);
        assertEquals(schemes[1],testSchemes[2]);
    }

    /**
    * Test method to verify the behavior of {@code getScheme(int)} method in {@code VillainFileDAO}.
    * It invokes the method with a scheme ID and checks the returned scheme.
    */
    @Test
    public void testGetScheme() {
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme(99);

        // Analzye
        assertEquals(scheme,testSchemes[0]);
    }

    /**
    * Test method to verify the behavior of {@code deleteScheme(int)} method in {@code VillainFileDAO}.
    * It invokes the method to delete a scheme and checks the result and internal state.
    */
    @Test
    public void testDeleteScheme() {  //changed schemes to public to make this work, see if this does anything.
        // Invoke
        boolean result = assertDoesNotThrow(() -> VillainFileDAO.deleteScheme(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(VillainFileDAO.schemes.size(),testSchemes.length-1);
    }

    /**
    * Test method to verify the behavior of {@code createScheme(Scheme)} method in {@code VillainFileDAO}.
    * It creates a new scheme, invokes the method, and checks the result and the scheme's presence.
    */
    @Test
    public void testCreateScheme() {
        // Setup
        Scheme scheme = new Scheme(102, "clayman", "world to clay");

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.createScheme(scheme),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        // Verify the existence of the created scheme by name
        Scheme actual = VillainFileDAO.getScheme_str(scheme.getName());
        assertNotNull(actual);
        assertEquals(scheme.getId(), actual.getId());
        assertEquals(scheme.getName(), actual.getName());
        assertEquals(scheme.getTitle(), actual.getTitle());
    }

    /**
    * Test method to verify the behavior of {@code updateScheme(Scheme)} method in {@code VillainFileDAO}.
    * It updates an existing scheme, invokes the method, and checks the result and the updated scheme.
    */
    @Test
    public void testUpdateScheme() {
        // Setup
        Scheme scheme = new Scheme(99,"Omni-Man", "Conquer Earth");

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.updateScheme(scheme),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Scheme actual = VillainFileDAO.getScheme(scheme.getId());
        assertEquals(actual,scheme);
    }

    /**
    * Test method to verify the exception handling in {@code createScheme(Scheme)} method of {@code VillainFileDAO}.
    * It simulates an IOException during saving and verifies that the exception is properly thrown.
    * @throws IOException if an error occurs during the test
    */
    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Scheme[].class));

        Scheme scheme = new Scheme(102,"Wi-Fire", "spread fire");

        assertThrows(IOException.class,
                        () -> VillainFileDAO.createScheme(scheme),
                        "IOException not thrown");
    }
    
    /**
     * Test method to verify the behavior when a scheme is not found in {@code VillainFileDAO}.
     * It invokes the method to retrieve a scheme with a non-existing ID and checks the result.
     */
    @Test
    public void testGetHeroNotFound() {
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme(98);

        // Analyze
        assertEquals(scheme,null);
    }

    /**
     * Test method to verify the behavior when deleting a non-existing scheme in {@code VillainFileDAO}.
     * It invokes the method to delete a scheme with a non-existing ID and checks the result and internal state.
     */
    @Test
    public void testDeleteHeroNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> VillainFileDAO.deleteScheme(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(VillainFileDAO.schemes.size(),testSchemes.length);
    }

    /**
     * Test method to verify the behavior when updating a non-existing scheme in {@code VillainFileDAO}.
     * It invokes the method to update a scheme with a non-existing ID and checks the result.
     */
    @Test
    public void testUpdateHeroNotFound() {
        // Setup
        Scheme scheme = new Scheme(98,"Bolt", "Destroy cats");

        // Invoke
        Scheme result = assertDoesNotThrow(() -> VillainFileDAO.updateScheme(scheme), "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    /**
     * Test method to verify the exception handling in the constructor of {@code VillainFileDAO}.
     * It simulates an IOException during initialization and verifies that the exception is properly thrown.
     * @throws IOException if an error occurs during the test
     */
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Scheme[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new VillainFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}