package com.ufund.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.model.VillainSchemeNeed;

/**
 * Implements the functionality for JSON file-based persistence for Villain Schemes
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author [Your Name]
 */
@Component
public class VillainFileDAO implements VillainDAO {
    private static final Logger LOG = Logger.getLogger(VillainFileDAO.class.getName());
    private static final Logger LOG1 = Logger.getLogger(VillainSchemeNeed.class.getSimpleName());

    private Map<Integer, VillainSchemeNeed> schemes;
    private ObjectMapper objectMapper;
   
    private static int nextId;
   
    private String filename;

    /**
     * Creates a Villain File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public VillainFileDAO(@Value("${villains.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the schemes from the file
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private VillainSchemeNeed[] getSchemesArray() {
        return schemes.values().toArray(new VillainSchemeNeed[0]);
    }

    private VillainSchemeNeed[] findSchemesArray(String containsScheme) {
        ArrayList<VillainSchemeNeed> schemeList = new ArrayList<>();

        for (VillainSchemeNeed scheme : schemes.values()) {
            if (containsScheme == null || scheme.getName().contains(containsScheme)) {
                schemeList.add(scheme);
            }
        }
        VillainSchemeNeed[] schemeArray = new VillainSchemeNeed[schemeList.size()]; //not used(for now I guess)

        return schemeList.toArray(new VillainSchemeNeed[0]);
    }

    private boolean save() throws IOException {
        VillainSchemeNeed[] schemeArray = getSchemesArray();
        objectMapper.writeValue(new File(filename), schemeArray);
        return true;
    }

    private boolean load() throws IOException {
        schemes = new TreeMap<>();
        nextId = 0;

        VillainSchemeNeed[] schemeArray = objectMapper.readValue(new File(filename), VillainSchemeNeed[].class);

        for (VillainSchemeNeed scheme : schemeArray) {
            schemes.put(scheme.getId(), scheme);
            if (scheme.getId() > nextId)
                nextId = scheme.getId();
        }

        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public VillainSchemeNeed[] getSchemes() {
        synchronized (schemes) {
            return getSchemesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public VillainSchemeNeed[] findSchemes(String containsScheme) {
        synchronized (schemes) {
            return findSchemesArray(containsScheme);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public VillainSchemeNeed getScheme(int id) {
        synchronized (schemes) {
            return schemes.get(id);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public VillainSchemeNeed createScheme(VillainSchemeNeed scheme) throws IOException {
        synchronized (schemes) {
            VillainSchemeNeed newScheme = new VillainSchemeNeed(nextId(), scheme.getName(), scheme.getTitle());
            schemes.put(newScheme.getId(), newScheme);
            save();
            return newScheme;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public VillainSchemeNeed updateScheme(VillainSchemeNeed scheme) throws IOException {
        synchronized (schemes) {
            if (schemes.containsKey(scheme.getId())) {
                schemes.put(scheme.getId(), scheme);
                save();
                return scheme;
            } else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteScheme(int id) throws IOException {
        synchronized (schemes) {
            if (schemes.containsKey(id)) {
                schemes.remove(id);
                return save();
            } else {
                return false;
            }
        }
    }
}
