package com.ufund.api.ufundapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a villain entity
 * 
 * @author Isaac Soares, Jacy Chan, Nadeem Mustafa, 
 * Evan Kinsey, Anthony Visiko
 */
public class Scheme {
    private static final Logger LOG = Logger.getLogger(Scheme.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "Scheme [id=%d, name=%s, title=%s, fundgoal= %d]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("title") private String title;
    @JsonProperty("fundgoal") private int fundgoal;
    private boolean addedToCart;

    /**
     * Create a Scheme with the given id, name, and title. 
     * @param id The id of the villain
     * @param name The name of the villain
     * @param title The title of the villain's scheme
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Scheme(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("title") String title, @JsonProperty("fundgoal") int fundgoal) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.fundgoal = fundgoal;
        this.addedToCart = false;
    }

    /**
     * Retrieves the id of the villain
     * @return The id of the villain
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the villain
     * @return The name of the villain
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the title of the villain scheme
     * @return The title of the villain scheme
     */
    public String getTitle() {
        return title;
    }


    public int getfundgoal() {
        return fundgoal;
    }

     /**
     * Sets the name of the villain - necessary for JSON object to Java object deserialization
     * @param name The name of the villain
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the title of the villain scheme - necessary for JSON object to Java object deserialization
     * @param title The title of the villain scheme
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setFundgoal(int fundgoal) {
        this.fundgoal = fundgoal;
    }

    public boolean isAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        this.addedToCart = addedToCart;
    }

    /**
     * Returns a string representation of the configuration.
     * @return string representation.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, title, fundgoal);
    }

}
