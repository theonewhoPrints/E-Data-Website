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

    Map<Integer, Scheme> schemes;  // Provides a local cache of the Scheme objects
    // so that we don't need to read from the file
    // each time

    private ObjectMapper objectMapper;  // Provides conversion between Scheme
    // objects and JSON text format written
    // to the file
   
    private static int nextId;  // The next Id to assign to a new Scheme
   
    private String filename; // Filename to read from and write to

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

    /**
     * Generates the next id for a new {@linkplain Scheme villain}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Scheme villains} from the tree map
     * 
     * @return  The array of {@link Scheme villains}, may be empty
     */
    private Scheme[] getSchemesArray() {
        return schemes.values().toArray(new Scheme[0]);
    }

    /**
     * Finds an array from {@linkplain Scheme villains} from the tree map
     * 
     * @param containsScheme The text to match against
     * 
     * @return An array of {@link Scheme villains} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
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

    /**
     * Saves the {@linkplain Scheme villains} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Scheme villains} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Scheme[] schemeArray = getSchemesArray();
        objectMapper.writeValue(new File(filename), schemeArray);
        return true;
    }

    /**
     * Loads {@linkplain Scheme villains} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
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

    /**
    ** {@inheritDoc}
     */
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
    public Scheme getScheme_str(String name) {
        synchronized (schemes) {
            return schemes.get(name);    
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Scheme createScheme(Scheme scheme) throws IOException {
        synchronized(schemes){
            Scheme exists = getScheme(scheme.getId());
            Scheme exists_name = getScheme_str(scheme.getName());
            
            if(exists == null || exists_name == null ){
                Scheme newScheme = new Scheme(nextId(), scheme.getName(), scheme.getTitle());
                schemes.put(newScheme.getId(), newScheme);
                save();
                return newScheme;
            }else{
                return null;
            }
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
