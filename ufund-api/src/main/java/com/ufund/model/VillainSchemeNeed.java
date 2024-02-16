package com.ufund.model;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VillainSchemeNeed {
    private static final Logger LOG = Logger.getLogger(VillainSchemeNeed.class.getName());
    
    static final String STRING_FORMAT = "Scheme [id=%d, name=%s, title=%s]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("title") private String title;

    public VillainSchemeNeed(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
