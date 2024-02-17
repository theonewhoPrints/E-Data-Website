package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.VillainDAO;
import com.ufund.api.ufundapi.model.VillainSchemeNeed;

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
    public void testGetHeroes() throws IOException { // getHeroes may throw IOException
        // Setup
        VillainSchemeNeed[] heroes = new Hero[2];
        heroes[0] = new Hero(99,"Bolt");
        heroes[1] = new Hero(100,"The Great Iguana");
        // When getHeroes is called return the heroes created above
        when(mockHeroDAO.getHeroes()).thenReturn(heroes);

        // Invoke
        ResponseEntity<Hero[]> response = heroController.getHeroes();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(heroes,response.getBody());
    }

    @Test
    public void testGetHeroesHandleException() throws IOException { // getHeroes may throw IOException
        // Setup
        // When getHeroes is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockHeroDAO).getHeroes();

        // Invoke
        ResponseEntity<Hero[]> response = heroController.getHeroes();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}