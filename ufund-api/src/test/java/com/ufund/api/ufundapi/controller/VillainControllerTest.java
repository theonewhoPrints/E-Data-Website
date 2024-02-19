package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.VillainDAO;
import com.ufund.api.ufundapi.model.Scheme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Hero Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class VillainControllerTest {
    private VillainController villainController;
    private VillainDAO mockVillainDAO;

    /**
     * Before each test, create a new HeroController object and inject
     * a mock Hero DAO
     */
    @BeforeEach
    public void setupVillainController() {
        mockVillainDAO = mock(VillainDAO.class);
        villainController = new VillainController(mockVillainDAO);
    }

    @Test
    public void testGetSchemes() throws IOException { // getSchemes may throw IOException
        // Setup
        Scheme[] schemes = new Scheme[2];
        schemes[0] = new Scheme(99,"Dr. Silly", "I want to drop banana peels everywhere");
        schemes[1] = new Scheme(100,"Dr. Man", "I DON'T want world peace");
        // When getSchemes is called return the schemes created above
        when(mockVillainDAO.getSchemes()).thenReturn(schemes);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.getVillains();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(schemes,response.getBody());
    }

    @Test
    public void testGetSchemesHandleException() throws IOException { // getSchemes may throw IOException
        // Setup
        // When getSchemes is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).getSchemes();

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.getVillains();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchVillainSchemesByName() throws IOException { // findSchemesByName may throw IOException
        // Setup
        // when findSchemesByName is called, it will return the name created below
        String searchName = "Dr. Sin";
        Scheme[] schemes = {
            new Scheme(141, "Dr. Sin", "I like to tp houses"),
            new Scheme(12, "Wonderful", "hello"),
            new Scheme(11, "Dr. Nefarious", "I want to kill people")
        };
    
        // Mock behavior of findSchemesByName method
        when(mockVillainDAO.findSchemesByName(searchName)).thenReturn(schemes);

        // Invoke the method under test
        ResponseEntity<Scheme[]> response = villainController.searchVillainSchemesByName(searchName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schemes, response.getBody());
    }
}
