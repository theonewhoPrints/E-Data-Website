package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Scheme;

/**
 * Defines the interface for Scheme object persistence
 * 
 * @author Jacky Chan & Isaac Soares 
 */
public interface VillainDAO {
    
    /**
     * Retrieves all {@linkplain Scheme villains}
     * 
     * @return An array of {@link Scheme villan} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme[] getSchemes() throws IOException;

    /**
     * Finds all {@linkplain Scheme villains} whose scheme contains the given text
     * 
     * @param containsScheme The text to match against
     * 
     * @return An array of {@link Scheme villains} whose names contains the given text, may be empty
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
     * Retrieves a {@linkplain Scheme villan} with the given name
     * 
     * @param name The name of the {@link Scheme villan} to get
     * 
     * @return a {@link Scheme villan} object with the matching name
     * <br>
     * null if no {@link Scheme villan} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme getScheme_str(String name) throws IOException;

    /**
     * Creates and saves a {@linkplain Scheme villan}
     * 
     * @param Scheme {@linkplain Scheme villan} object to be created and saved
     * <br>
     * The id of the scheme object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Scheme villan} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme createScheme(Scheme scheme) throws IOException;

    /**
     * Updates and saves a {@linkplain Scheme villan}
     * 
     * @param {@link Scheme villan} object to be updated and saved
     * 
     * @return updated {@link Scheme villan} if successful, null if
     * {@link Scheme villan} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Scheme updateScheme(Scheme scheme) throws IOException;
    
    /**
     * Finds all {@linkplain Scheme villains} whose title contains the given text
     * 
     * @param title The text to match against
     * 
     * @return An array of {@link Scheme villains} whose title contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme[] findSchemesByTitle(String title) throws IOException;

    /**
     * Finds all {@linkplain Scheme villains} whose name contains the given text
     * 
     * @param name The text to match against
     * 
     * @return An array of {@link Scheme villains} whose name contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Scheme[] findSchemesByName(String name) throws IOException;

    /**
     * Deletes a {@linkplain Scheme villan} with the given id
     * 
     * @param id The id of the {@link Scheme villan}
     * 
     * @return true if the {@link Scheme villan} was deleted
     * <br>
     * false if villain with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteScheme(int id) throws IOException;
}