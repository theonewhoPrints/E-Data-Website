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

@Tag("Controller-Tier")
public class VillainControllerTest {
    private VillainController villainController;
    private VillainDAO mockVillainDAO;

    @BeforeEach
    public void setupVillainController() {
        mockVillainDAO = mock(VillainDAO.class);
        villainController = new VillainController(mockVillainDAO);
    }

    @Test
    public void testCreateVillain() throws IOException {  // createScheme may throw IOException
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
    public void testCreateVillainFailed() throws IOException {  // createScheme may throw IOException
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
    public void testCreateVillainHandleException() throws IOException {  // createScheme may throw IOException
        // Setup
        Scheme schemes = new Scheme(99,"Aizen","Part of the plan");

        // When createScheme is called on the Mock Villain DAO, throw an IOException
        doThrow(new IOException()).when(mockVillainDAO).createScheme(schemes);

        // Invoke
        ResponseEntity<Scheme> response = villainController.createVillain(schemes);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
