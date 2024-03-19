package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based persistence for Users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Evan Kinsey
 */
@Component
public class UserFileDAO implements UserDAO {

    Map<Integer, User> users;  // Provides a local cache of the User objects
    // so that we don't need to read from the file
    // each time

    private ObjectMapper objectMapper;  // Provides conversion between User
    // objects and JSON text format written
    // to the file
   
    private static int nextId;  // The next Id to assign to a new User
   
    private String filename; // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return users.values().toArray(new User[0]);
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }

        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User findUser(String name) {
        synchronized (users) {
            for (User user : users.values()) {
                if (user.getName().equals(name)) {
                    return user;
                }
            }
            // If user not found, return null or throw an exception
            return null;
        }
    }
}
