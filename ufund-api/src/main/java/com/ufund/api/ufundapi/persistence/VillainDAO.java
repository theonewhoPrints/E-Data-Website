package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Scheme;

/**
 * Defines the interface for Hero object persistence
 * 
 * @author Jacky Chan & Isaac Soares 
 */
public interface VillainDAO {
    /**
     * Retrieves all {@linkplain Scheme villans}
     * 
     * @return An array of {@link Scheme villan} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme[] getSchemes() throws IOException;

    /**
     * Finds all {@linkplain Scheme villans} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Scheme villans} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme[] findSchemes(String containsScheme) throws IOException;
    
    /**
     * Retrieves a {@linkplain Scheme villan} with the given id
     * 
     * @param id The id of the {@link Scheme villan} to get
     * 
     * @return a {@link Scheme villan} object with the matching id
     * <br>
     * null if no {@link Scheme villan} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme getScheme(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Scheme villan}
     * 
     * @param hero {@linkplain Scheme villan} object to be created and saved
     * <br>
     * The id of the hero object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Scheme villan} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme createScheme(Scheme scheme) throws IOException;
    

    Scheme updateScheme(Scheme scheme) throws IOException;

    Scheme[] findSchemesByTitle(String title) throws IOException;

    Scheme[] findSchemesByName(String name) throws IOException;

    boolean deleteScheme(int id) throws IOException;
}