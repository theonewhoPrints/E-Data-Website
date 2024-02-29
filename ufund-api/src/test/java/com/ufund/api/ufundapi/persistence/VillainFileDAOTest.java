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
        testSchemes[1] = new Scheme(100,"Agent00", "Freeze 2K20");
        testSchemes[2] = new Scheme(101,"TSift", "waste all carbon emissions");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Scheme[].class))
                .thenReturn(testSchemes);
        VillainFileDAO = new VillainFileDAO("doesnt_matter.txt",mockObjectMapper);

    }

    @Test
    public void testGetSchemes() {
        // Invoke
        Scheme[] schemes = VillainFileDAO.getSchemes();

        // Analyze
        assertEquals(schemes.length,testSchemes.length);
        for (int i = 0; i < testSchemes.length;++i)
            assertEquals(schemes[i],testSchemes[i]);
    }

    @Test
    public void testFindSchemes() {   //may have to make more for different finds in each text.
        // Invoke
        Scheme[] schemes = VillainFileDAO.findSchemes("Freeze");

        // Analyze
        assertEquals(schemes.length,2);
        assertEquals(schemes[0],testSchemes[1]);
        assertEquals(schemes[1],testSchemes[2]);
    }

    @Test
    public void testGetScheme() {
        // Invoke
        Scheme scheme = VillainFileDAO.getScheme(99);

        // Analzye
        assertEquals(scheme,testSchemes[0]);
    }

    @Test
    public void testDeleteScheme() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> VillainFileDAO.deleteScheme(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(VIllainFileDAO.schemes.size(),testSchemes.length-1);
    }






}