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
 * /
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
    public void testCreateScheme() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(13,"Sukuna","Save me Mahoraga!");
        // when createScheme is called, return true simulating successful
        // creation and save
        when(mockVillainDAO.createScheme(schemes)).thenReturn(schemes);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);


        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(schemes,response.getBody());
    }


    @Test
    public void testCreateSchemeFailed() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(99,"Gojo", "Nah I'd win");
        // when createScheme is called, return false simulating failed
        // creation and save
        when(mockVillainDAO.createScheme(schemes)).thenReturn(null);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);


        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }


    @Test
    public void testCreateSchemeHandleException() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(99,"Aizen","Part of the plan");


        // When createScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).createScheme(schemes);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);

    }
    
    @Test
    public void testDeleteScheme() throws IOException { // deleteScheme may throw IOException
        // Setup
        int schemeId = 99;
        // when deleteScheme is called return true, simulating successful deletion
        when(mockVillainDAO.deleteScheme(schemeId)).thenReturn(true);

        // Invoke
        ResponseEntity<Void> response = villainController.deleteVillain(schemeId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

      
    @Test
    public void testDeleteHeroNotFound() throws IOException { // deleteScheme may throw IOException
        // Setup
        int schemeId = 99;
        // when deleteScheme is called return false, simulating failed deletion
        when(mockVillainDAO.deleteScheme(schemeId)).thenReturn(false);

        // Invoke
        ResponseEntity<Void> response = villainController.deleteVillain(schemeId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

      
    @Test
    public void testDeleteHeroHandleException() throws IOException { // deleteScheme may throw IOException
        // Setup
        int schemeId = 99;
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).deleteScheme(schemeId);

        // Invoke
        ResponseEntity<Void> response = villainController.deleteVillain(schemeId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    
}