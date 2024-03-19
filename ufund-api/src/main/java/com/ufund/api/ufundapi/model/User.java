package com.ufund.api.ufundapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a user entity
 * 
 * @author Evan Kinsey
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, name=%s, role=%s]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("role") private String role;

    /**
     * Create a user with the given id and name. 
     * @param id The id of the user
     * @param name The name of the user
     * @param role The role of the user
     *
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("role") String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the user
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the role of the user
     * @return The role of the user
     */
    public String getRole() {
        return role;
    }

     /**
     * Sets the name of the user - necessary for JSON object to Java object deserialization
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the role of the user - necessary for JSON object to Java object deserialization
     * @param role The role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the configuration.
     * @return string representation.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, role);
    }

}
