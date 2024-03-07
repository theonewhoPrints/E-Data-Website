package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

/**
 * Defines the interface for User object persistence
 * 
 * @author Evan Kinsey
 */
public interface UserDAO {
    
    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a {@linkplain User user} with the given name
     * 
     * @param name The name of the {@link User user} to get
     * 
     * @return a {@link User user} object with the matching name
     * <br>
     * null if no {@link User user} with a matching name is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User findUser(String name) throws IOException;
}