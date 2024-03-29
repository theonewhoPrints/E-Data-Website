package com.ufund.api.ufundapi.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a user entity
 * 
 * @author Evan Kinsey
 */
public class User {
    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, name=%s, role=%s, imgUrl=%s, description=%s, achievements=%s]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("role") private String role;
    @JsonProperty("imgUrl") private String imgUrl;
    @JsonProperty("description") private String description;
    @JsonProperty("achievements") private List<String> achievements;

    /**
     * Create a user with the given id and name. 
     * @param id The id of the user
     * @param name The name of the user
     * @param role The role of the user
     * @param imgUrl The image URL of the user
     * @param description The description of the user
     * @param achievements The list of achievements of the user
     * 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("role") String role, @JsonProperty("imgUrl") String imgUrl, @JsonProperty("description") String description, @JsonProperty("achievements") List<String> achievements){
        this.id = id;
        this.name = name;
        this.role = role;
        this.imgUrl = imgUrl;
        this.description = description;
        this.achievements = achievements;
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
     * Retrieves the image URL of the user
     * @return The image URL of the user
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Sets the image URL of the user
     * @param imgUrl The image URL of the user
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Retrieves the description of the user
     * @return The description of the user
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the user
     * @param description The description of the user
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the list of achievements of the user
     * @return The list of achievements of the user
     */
    public List<String> getAchievements() {
        return achievements;
    }

    /**
     * Adds an achievement to the user's list of achievements
     * @param achievement The achievement to be added
     */
    public void addAchievement(String achievement) {
        this.achievements.add(achievement);
    }

    /**
     * Edits an achievement in the user's list of achievements
     * @param achievement The edited achievement
     * @param index The index of the achievement to be edited
     */
    public void editAchievement(String achievement, int index) {
        this.achievements.set(index, achievement);
    }

    /**
     * Removes an achievement from the user's list of achievements
     * @param index The index of the achievement to be removed
     */
    public void removeAchievement(int index) {
        this.achievements.remove(index);
    }

    /**
     * Returns a string representation of the configuration.
     * @return string representation.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, role, imgUrl, description, achievements);
    }

}
