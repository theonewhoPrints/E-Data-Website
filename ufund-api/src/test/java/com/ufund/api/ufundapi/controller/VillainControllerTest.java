package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
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
 * @author Isaac Soares 
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

    /**
     * Tests retrieving all schemes successfully from {@link VillainController}.
     * 
     * Verifies that the correct schemes are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during scheme retrieval
     */
    @Test
    public void testGetSchemes() throws IOException { // getSchemes may throw IOException
        // Setup
        Scheme[] schemes = new Scheme[2];
        schemes[0] = new Scheme(99,"Dr. Silly", "I want to drop banana peels everywhere", 44000);
        schemes[1] = new Scheme(100,"Dr. Man", "I DON'T want world peace", 42000);
        // When getSchemes is called return the schemes created above
        when(mockVillainDAO.getSchemes()).thenReturn(schemes);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.getVillains();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(schemes,response.getBody());
    }

    /**
     * Tests exception handling in {@link VillainController#getSchemes()} when an I/O error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during scheme retrieval
     */
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

    /**
     * Tests retrieving a specific scheme by ID in {@link VillainController}.
     * 
     * Verifies that the correct scheme is returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during scheme retrieval
     */
    @Test
    public void testGetScheme() throws IOException {  // getScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Mr. Frozen", "I must freeze all of the people on earth!!", 40000);
        // When the same id is passed in, our mock Villain DAO will return the Scheme object
        when(mockVillainDAO.getScheme(scheme.getId())).thenReturn(scheme);

        // Invoke
        ResponseEntity<Scheme> response = villainController.getVillain(scheme.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(scheme,response.getBody());
    }

    /**
     * Tests retrieving a scheme by ID when the scheme is not found.
     * 
     * Ensures that the HTTP status is NOT_FOUND when the requested scheme does not exist.
     * 
     * @throws Exception if an error occurs during scheme retrieval
     */
    @Test
    public void testGetSchemeNotFound() throws Exception { // createScheme may throw IOException
        // Setup
        int schemeID = 99;
        // When the same id is passed in, our mock Villain DAO will return null, simulating
        // no scheme found
        when(mockVillainDAO.getScheme(schemeID)).thenReturn(null);

        // Invoke
        ResponseEntity<Scheme> response = villainController.getVillain(schemeID);
    }

    /**
     * Tests searching schemes by name successfully in {@link VillainController}.
     * 
     * Verifies that the correct schemes are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainSchemesByName() throws IOException { // findSchemesByName may throw IOException
        // Setup
        // when findSchemesByName is called, it will return the name created below
        String searchName = "Dr. Sin";
        Scheme[] schemes = {
            new Scheme(141, "Dr. Sin", "I like to tp houses", 38000),
            new Scheme(12, "Wonderful", "hello", 36000),
            new Scheme(11, "Dr. Nefarious", "I want to kill people", 34000)
        };
    
        // Mock behavior of findSchemesByName method
        when(mockVillainDAO.findSchemesByName(searchName)).thenReturn(schemes);

        // Invoke the method under test
        ResponseEntity<Scheme[]> response = villainController.searchVillainSchemesByName(searchName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schemes, response.getBody());
    }

    /**
     * Tests searching schemes by name when schemes are found.
     * 
     * Verifies that the correct schemes are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainSchemesByNameSuccess() throws IOException {
        // Setup
        String searchName = "Dr. Evil";
        Scheme[] expectedSchemes = { new Scheme(1, "Dr. Evil", "World domination", 32000) };
        when(mockVillainDAO.findSchemesByName(searchName)).thenReturn(expectedSchemes);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillainSchemesByName(searchName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSchemes, response.getBody());
    }

    /**
     * Tests searching schemes by name when no schemes are found.
     * 
     * Verifies that an empty array is returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainSchemesByNameNotFound() throws IOException {
        // Setup
        String searchName = "Unknown";
        when(mockVillainDAO.findSchemesByName(searchName)).thenReturn(new Scheme[0]);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillainSchemesByName(searchName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().length);
    }

    /**
     * Tests exception handling in searching schemes by name in {@link VillainController}.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainSchemesByNameException() throws IOException {
        // Setup
        String searchName = "Dr. Chaos";
        doThrow(new IOException("Database error")).when(mockVillainDAO).findSchemesByName(searchName);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillainSchemesByName(searchName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests creating a scheme successfully in {@link VillainController}.
     * 
     * Verifies that a scheme is created successfully and the HTTP status is CREATED.
     * 
     * @throws IOException if an I/O error occurs during scheme creation
     */
    @Test
    public void testCreateScheme() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(13,"Sukuna","Save me Mahoraga!", 30000);
        // when createScheme is called, return true simulating successful
        // creation and save
        when(mockVillainDAO.createScheme(schemes)).thenReturn(schemes);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);


        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(schemes,response.getBody());
    }

    /**
     * Tests creating a scheme in {@link VillainController} when the creation fails.
     * 
     * Simulates a scenario where scheme creation fails and checks if the HTTP status is CONFLICT.
     * 
     * @throws IOException if an I/O error occurs during scheme creation
     */
    @Test
    public void testCreateSchemeFailed() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(99,"Gojo", "Nah I'd win", 36000);
        // when createScheme is called, return false simulating failed
        // creation and save
        when(mockVillainDAO.createScheme(schemes)).thenReturn(null);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);


        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    /**
     * Tests exception handling in {@link VillainController#createVillain(Scheme)} when an error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during scheme creation
     */
    @Test
    public void testCreateSchemeHandleException() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(99,"Aizen","Part of the plan", 34000);


        // When createScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).createScheme(schemes);


        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);

    }
    
    /**
     * Tests deleting a scheme successfully in {@link VillainController}.
     * 
     * Verifies that a scheme is deleted successfully and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during scheme deletion
     */
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

    /**
     * Tests deleting a scheme in {@link VillainController} when the scheme does not exist.
     * 
     * Simulates a scenario where scheme deletion fails due to non-existence and checks if the HTTP status is NOT_FOUND.
     * 
     * @throws IOException if an I/O error occurs during scheme deletion
     */
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

    /**
     * Tests exception handling in {@link VillainController#getVillain(int)} when an error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws Exception if an error occurs during scheme retrieval
     */
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

    /**
     * Tests exception handling in {@link VillainController#deleteVillain(int)} when an error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during scheme deletion
     */
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

    /**
     * Tests updating a scheme successfully in {@link VillainController}.
     * 
     * Verifies that a scheme is updated successfully and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during scheme update
     */
    @Test
    public void testUpdateScheme() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Wi-Fire", "I'm going to destroy the internet", 32000);
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

    /**
     * Tests updating a scheme in {@link VillainController} when the update fails.
     * 
     * Simulates a scenario where scheme update fails and checks if the HTTP status is NOT_FOUND.
     * 
     * @throws IOException if an I/O error occurs during scheme update
     */
    @Test
    public void testUpdateSchemeFailed() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme scheme = new Scheme(99,"Galactic Agent", "I will eat the galaxy", 30000);
        // when updateScheme is called, return true simulating successful
        // update and save
        when(mockVillainDAO.updateScheme(scheme)).thenReturn(null);

        // Invoke
        ResponseEntity<Scheme> response = villainController.updateVillain(scheme);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    /**
     * Tests exception handling in {@link VillainController#updateVillain(Scheme)} when an error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during scheme update
     */
    @Test
    public void testUpdateSchemeHandleException() throws IOException { // updateScheme may throw IOException
        // Setup
        Scheme hero = new Scheme(99,"Galactic Agent", "I will eat the galaxy", 28000);
        // When updateScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).updateScheme(hero);

        // Invoke
        ResponseEntity<Scheme> response = villainController.updateVillain(hero);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Tests searching for villains by name in {@link VillainController}.
     * 
     * Verifies that the correct schemes are returned when searched by name and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsByName() throws IOException {
        // Setup
        String name = "Dr. Evil";
        Scheme[] expectedVillains = { new Scheme(1, name, "take over the world", 26000) };
        when(mockVillainDAO.findSchemesByName(name.toLowerCase())).thenReturn(expectedVillains);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(name.toLowerCase(), null);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVillains, response.getBody());
    }

    /**
     * Tests searching for schemes by title in {@link VillainController}.
     * 
     * Verifies that the correct schemes are returned when searched by title and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsByTitle() throws IOException {
        // Setup
        String title = "World Domination";
        Scheme[] expectedVillains = { new Scheme(1, "Dr. Evil", title, 24000) };
        when(mockVillainDAO.findSchemesByTitle(title.toLowerCase())).thenReturn(expectedVillains);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(null, title.toLowerCase()); // Ensure the title is converted to lowercase as done in the frontend

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVillains, response.getBody());
    }

    /**
     * Tests searching for schemes by both name and title in {@link VillainController}.
     * 
     * Verifies that the correct schemes are returned when searched by both name and title and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsByNameAndTitle() throws IOException {
        // Setup
        String name = "Dr. Evil";
        String title = "World Domination";
        Scheme[] expectedVillains = { new Scheme(1, name, title, 22000) };
        
        // Refactored stubbing
        when(mockVillainDAO.findSchemesByNameAndTitle(name.toLowerCase(), title.toLowerCase())).thenReturn(expectedVillains);
    
        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(name, title);
    
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVillains, response.getBody());
    }
    

    /**
     * Tests exception handling in {@link VillainController#searchVillains(String, String)} when an error occurs.
     * 
     * Simulates an IOException and checks if the HTTP status is INTERNAL_SERVER_ERROR.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsException() throws IOException {
        // Setup
        doThrow(new IOException("Database error")).when(mockVillainDAO).getSchemes();

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(null, null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests searching for villains with no parameters in {@link VillainController}.
     * 
     * Verifies that all available schemes are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsWithNoParameters() throws IOException {
        // Setup
        Scheme[] expectedVillains = new Scheme[] {
            new Scheme(1, "Dr. Generic", "Generic Plan", 20000),
            new Scheme(2, "Ms. Generic", "Another Generic Plan", 21000)
        };
        when(mockVillainDAO.getSchemes()).thenReturn(expectedVillains);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(null, null);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVillains, response.getBody(), "The method should return all villains when no parameters are given.");
    }

    /**
     * Tests searching for schemes by title only in {@link VillainController}.
     * 
     * Verifies that schemes matching the title are returned and the HTTP status is OK.
     * 
     * @throws IOException if an I/O error occurs during the search
     */
    @Test
    public void testSearchVillainsByTitleOnly() throws IOException {
        // Setup
        String title = "Global Warming";
        Scheme[] expectedVillains = { new Scheme(3, "Dr. Warm", title, 25000) };
        // Mocking case-insensitive search behavior
        when(mockVillainDAO.findSchemesByTitle(title.toLowerCase())).thenReturn(expectedVillains);

        // Invoke
        ResponseEntity<Scheme[]> response = villainController.searchVillains(null, title);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVillains, response.getBody(), "The method should return villains matching the title only.");
    }

}
