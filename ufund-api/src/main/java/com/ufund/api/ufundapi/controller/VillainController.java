package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Scheme;
import com.ufund.api.ufundapi.persistence.VillainDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles the REST API requests for the Villain Scheme resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Isaac Soares & Jacky Chan 
 */
@RestController
@RequestMapping("villains")
public class VillainController {
    private static final Logger LOG = Logger.getLogger(VillainController.class.getName());
    private VillainDAO villainDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param villainDao The {@link VillainDAO Villain Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public VillainController(VillainDAO villainDao) {
        this.villainDao = villainDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Scheme villain} for the given id
     * 
     * @param id The id used to locate the {@link Scheme villain}
     * 
     * @return ResponseEntity with {@link Scheme villain} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */
    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getVillain(@PathVariable int id) {
        LOG.info("GET /villains/" + id);
        try {
            Scheme villain = villainDao.getScheme(id);
            if (villain != null)
                return new ResponseEntity<>(villain, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Scheme villains}
     * 
     * @return ResponseEntity with array of {@link Scheme villain} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example usage in cURL: curl.exe -X GET 'http://localhost:8080/villains'
     * 
     * @author Evan Kinsey
     * 
     * test
     */
    @GetMapping("")
    public ResponseEntity<Scheme[]> getVillains() {
        LOG.info("GET /villains");
        try {
            Scheme[] villains = villainDao.getSchemes();
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Scheme villains} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Hero heroes}
     * 
     * @return ResponseEntity with array of {@link Scheme villain} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all heroes that contain the text "ma"
     * GET http://localhost:8080/heroes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Scheme[]> searchVillains(@RequestParam String name) {
        LOG.info("GET /villains/?name=" + name);
        try {
            Scheme[] villains = villainDao.findSchemes(name);
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/SearchByName")
    public ResponseEntity<Scheme[]> searchVillainSchemesByName(@RequestParam String name) {
        LOG.info("GET /villians/search?name=" + name);
        try {
            Scheme[] villains = villainDao.findSchemesByName(name);
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Scheme villain} with the provided hero object
     * 
     * @param hero - The {@link Scheme villain} to create
     * 
     * @return ResponseEntity with created {@link Scheme villain} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Scheme villain} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Scheme> createVillain(@RequestBody Scheme villain) {
        LOG.info("POST /villains " + villain);
        try {

            Scheme existingVillain = villainDao.getScheme(villain.getId());
            if (existingVillain != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Scheme createdVillain = villainDao.createScheme(villain);
            return new ResponseEntity<>(createdVillain, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Scheme villain} with the provided {@linkplain Scheme villain} object, if it exists
     * 
     * @param hero The {@link Scheme villain} to update
     * 
     * @return ResponseEntity with updated {@link Scheme villain} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Scheme> updateVillain(@RequestBody Scheme villain) {
        LOG.info("PUT /villains " + villain);
        try {
            Scheme updatedVillain = villainDao.updateScheme(villain);
            if (updatedVillain != null)
                return new ResponseEntity<>(updatedVillain, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Scheme villain} with the given id
     * 
     * @param id The id of the {@link Scheme villain} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVillain(@PathVariable int id) {
        LOG.info("DELETE /villains/" + id);
        try {
            boolean deleted = villainDao.deleteScheme(id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
