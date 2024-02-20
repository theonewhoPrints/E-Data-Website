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
    public void testGetScheme() throws IOException {  // getScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Mr. Frozen", "I must freeze all of the people on earth!!");
        // When the same id is passed in, our mock Villain DAO will return the Scheme object
        when(mockVillainDAO.getScheme(scheme.getId())).thenReturn(scheme);

        // Invoke
        ResponseEntity<Scheme> response = villainController.getVillain(scheme.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(scheme,response.getBody());
    }

    @Test
    public void testGetSchemeNotFound() throws Exception { // createScheme may throw IOException
        // Setup
        int schemeID = 99;
        // When the same id is passed in, our mock Villain DAO will return null, simulating
        // no scheme found
        when(mockVillainDAO.getScheme(schemeID)).thenReturn(null);

        // Invoke
        ResponseEntity<Scheme> response = villainController.getVillain(schemeID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetSchemeHandleException() throws Exception { // createHero may throw IOException
        // Setup
        int schemeID = 99;
        // When getScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).getScheme(schemeID);

        // Invoke
        ResponseEntity<Scheme> response = villainController.getVillain(schemeID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateScheme() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Wi-Fire", "I'm going to destroy the internet");
        // when updateScheme is called, return true simulating successful
        // update and save
        when(mockVillainDAO.updateScheme(scheme)).thenReturn(scheme);
        ResponseEntity<Scheme> response = villainController.updateVillain(scheme);
        scheme.setName("Wiki-don't");

        // Invoke
        response = villainController.updateVillain(scheme);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(scheme,response.getBody());
    }

    @Test
    public void testUpdateSchemeFailed() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Galactic Agent", "I will eat the galaxy");
        // when updateScheme is called, return true simulating successful
        // update and save
        when(mockVillainDAO.updateScheme(scheme)).thenReturn(null);

        // Invoke
        ResponseEntity<Scheme> response = villainController.updateVillain(scheme);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateSchemeHandleException() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme hero = new Scheme(99,"Galactic Agent", "I will eat the galaxy");
        // When updateScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).updateScheme(hero);

        // Invoke
        ResponseEntity<Scheme> response = villainController.updateVillain(hero);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}