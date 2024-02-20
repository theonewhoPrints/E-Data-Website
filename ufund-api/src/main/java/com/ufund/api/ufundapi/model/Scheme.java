package com.ufund.api.ufundapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Hero entity
 * 
 * @author Isaac Soares, Jacy Chan, Nadeem Mustafa, 
 * Evan Kinsey, Anthony Anthony Visiko
 */
public class Scheme {
    private static final Logger LOG = Logger.getLogger(Scheme.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "Scheme [id=%d, name=%s, title=%s]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("title") private String title;

    /**
     * Create a hero with the given id and name
     * @param id The id of the hero
     * @param name The name of the hero
     * @param title
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Scheme(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("title") String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    /**
     * Retrieves the id of the hero
     * @return The id of the hero
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the hero
     * @return The name of the hero
     */
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

     /**
     * Sets the name of the hero - necessary for JSON object to Java object deserialization
     * @param name The name of the hero
     */
    public void setName() {
        this.name = name;
    }

    public void setTitle() {
        this.title = title;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, title);
    }

}
