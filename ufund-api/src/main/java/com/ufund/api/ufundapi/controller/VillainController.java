package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.VillainSchemeNeed;
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
 * @author [Your Name]
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

    // GET /villains/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VillainSchemeNeed> getVillain(@PathVariable int id) {
        LOG.info("GET /villains/" + id);
        try {
            VillainSchemeNeed villain = villainDao.getScheme(id);
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
     * Responds to the GET request for all {@linkplain VillainSchemeNeed schemes}
     * 
     * @return ResponseEntity with array of {@link VillainSchemeNeed schemes} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * Example usage in cURL: curl.exe -X GET 'http://localhost:8080/villains'
     * 
     * @author Evan Kinsey
     */
    @GetMapping("")
    public ResponseEntity<VillainSchemeNeed[]> getVillains() {
        LOG.info("GET /villains");
        try {
            VillainSchemeNeed[] villains = villainDao.getSchemes();
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /villains/?name={name}
    @GetMapping("/")
    public ResponseEntity<VillainSchemeNeed[]> searchVillains(@RequestParam String name) {
        LOG.info("GET /villains/?name=" + name);
        try {
            VillainSchemeNeed[] villains = villainDao.findSchemes(name);
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // GET /villains/?title={title}
    /*
    @GetMapping("/")
    public ResponseEntity<VillainSchemeNeed[]> searchVillainsByTitle(@RequestParam String title) {
        LOG.info("GET /villains/?title=" + title);
        try {
            VillainSchemeNeed[] villains = villainDao.findSchemesByTitle(title);
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

    // GET /villians/search?name={name}
    @GetMapping("/SearchByName")
    public ResponseEntity<VillainSchemeNeed[]> searchVillainSchemesByName(@RequestParam String name) {
        LOG.info("GET /villians/search?name=" + name);
        try {
            VillainSchemeNeed[] villains = villainDao.findSchemesByName(name);
            return new ResponseEntity<>(villains, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /villains
    @PostMapping("")
    public ResponseEntity<VillainSchemeNeed> createVillain(@RequestBody VillainSchemeNeed villain) {
        LOG.info("POST /villains " + villain);
        try {

            VillainSchemeNeed existingVillain = villainDao.getScheme(villain.getId());
            if (existingVillain != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            VillainSchemeNeed createdVillain = villainDao.createScheme(villain);
            return new ResponseEntity<>(createdVillain, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /villains
    @PutMapping("")
    public ResponseEntity<VillainSchemeNeed> updateVillain(@RequestBody VillainSchemeNeed villain) {
        LOG.info("PUT /villains " + villain);
        try {
            VillainSchemeNeed updatedVillain = villainDao.updateScheme(villain);
            if (updatedVillain != null)
                return new ResponseEntity<>(updatedVillain, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /villains/{id}
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
