package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Scheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based persistence for Villain Schemes
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Isaac & Jacky 
 */
@Component
public class VillainFileDAO implements VillainDAO {
    private static final Logger LOG = Logger.getLogger(VillainFileDAO.class.getName());
    private static final Logger LOG1 = Logger.getLogger(Scheme.class.getSimpleName());

    private Map<Integer, Scheme> schemes;
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

    private Scheme[] getSchemesArray() {
        return schemes.values().toArray(new Scheme[0]);
    }

    private Scheme[] findSchemesArray(String containsScheme) {
        ArrayList<Scheme> schemeList = new ArrayList<>();

        for (Scheme scheme : schemes.values()) {
            if (containsScheme == null || scheme.getName().contains(containsScheme)) {
                schemeList.add(scheme);
            }
        }
        Scheme[] schemeArray = new Scheme[schemeList.size()]; //not used(for now I guess)

        return schemeList.toArray(new Scheme[0]);
    }

    private boolean save() throws IOException {
        Scheme[] schemeArray = getSchemesArray();
        objectMapper.writeValue(new File(filename), schemeArray);
        return true;
    }

    private boolean load() throws IOException {
        schemes = new TreeMap<>();
        nextId = 0;

        Scheme[] schemeArray = objectMapper.readValue(new File(filename), Scheme[].class);

        for (Scheme scheme : schemeArray) {
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
    public Scheme[] getSchemes() {
        synchronized (schemes) {
            return getSchemesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Scheme[] findSchemes(String containsScheme) {
        synchronized (schemes) {
            return findSchemesArray(containsScheme);
        }
    }

    // VillainFileDAO class
    @Override
    public Scheme[] findSchemesByTitle(String title) {  ///remove if not needed.
        synchronized (schemes) {
            ArrayList<Scheme> schemeList = new ArrayList<>();
            for (Scheme scheme : schemes.values()) {
                if (scheme.getTitle() != null && scheme.getTitle().contains(title)) {
                    schemeList.add(scheme);
                }
            }
            return schemeList.toArray(new Scheme[0]);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Scheme getScheme(int id) {
        synchronized (schemes) {
            return schemes.get(id);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Scheme createScheme(Scheme scheme) throws IOException {
        synchronized (schemes) {
            Scheme newScheme = new Scheme(nextId(), scheme.getName(), scheme.getTitle());
            schemes.put(newScheme.getId(), newScheme);
            save();
            return newScheme;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Scheme updateScheme(Scheme scheme) throws IOException {
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
    public Scheme[] findSchemesByName(String name) throws IOException {
        synchronized (schemes) {
            ArrayList<Scheme> schemeList = new ArrayList<>();
            for (Scheme scheme : schemes.values()) {
                if(scheme.getName() != null && scheme.getName().contains(name)) {
                    schemeList.add(scheme);
                }   
            }
            return schemeList.toArray(new Scheme[0]);
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
